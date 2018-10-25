package de.dhbw.studienarbeit.data.helper;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		}, maxUpdatesPerMinute);

		Thread.sleep(6000);

		assertTrue(updates <= 7);
	}
}
