package de.dhbw.studienarbeit.data.helper.datamanagement;

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

	final Manageable data = new Manageable()
	{
		@Override
		public Date nextUpdate()
		{
			return nextUpdate;
		}

		@Override
		public void updateAndSaveData(ApiKey apiKey) throws IOException
		{
			updates++;
		}
	};

	@Test
	void testCountUpdateIsLessThanMaximum() throws Exception
	{
		final List<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey("foo", 60, "bar"));

		final DataManager manager = new DataManager("no name", apiKeys);
		manager.add(data);

		Thread.sleep(6000);

		assertTrue(updates <= 7);
		assertTrue(updates > 5);
	}
}
