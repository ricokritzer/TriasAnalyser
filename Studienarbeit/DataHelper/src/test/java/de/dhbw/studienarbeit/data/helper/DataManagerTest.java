package de.dhbw.studienarbeit.data.helper;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class DataManagerTest
{
	int updates = 0;
	Date nextUpdate = new Date();
	boolean saved = false;

	final DataModel data = new DataModel()
	{
		@Override
		public void updateData(int attempts) throws IOException
		{
			updates++;
		}

		@Override
		public Date nextUpdate()
		{
			return nextUpdate;
		}

		@Override
		public String getSQLQuerry()
		{
			return "test";
		}
	};

	@Test
	void testCountUpdateIsLessThanMaximum() throws Exception
	{
		final int maxUpdatesPerMinute = 60;
		List<DataModel> models = new ArrayList<>();
		models.add(data);
		new DataManager(models, new Saver()
		{
			@Override
			public void save(DataModel model)
			{
				saved = true;
			}
		}, 60);

		Thread.sleep(60000 / 10);

		assertThat(updates, Is.is(maxUpdatesPerMinute / 10));
	}
}
