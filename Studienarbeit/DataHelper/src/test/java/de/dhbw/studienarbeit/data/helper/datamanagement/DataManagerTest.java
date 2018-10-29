package de.dhbw.studienarbeit.data.helper.datamanagement;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.Saver;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;

public class DataManagerTest
{
	int updates = 0;
	Date nextUpdate = new Date();
	boolean saved = false;

	final DataModel data = new DataModel()
	{
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

		@Override
		public void updateData(ApiKey apiKey) throws IOException
		{
			updates++;
		}
	};

	@Test
	void testCountUpdateIsLessThanMaximum() throws Exception
	{
		final List<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey("foo", 60));

		final DataManager manager = new DataManager(getSaverMock(), apiKeys);
		manager.add(data);

		Thread.sleep(6000);

		assertTrue(updates <= 7);
	}

	private Saver getSaverMock()
	{
		return new Saver()
		{
			@Override
			public void save(DataModel model)
			{
				// do nothing
			}
		};
	}
}
