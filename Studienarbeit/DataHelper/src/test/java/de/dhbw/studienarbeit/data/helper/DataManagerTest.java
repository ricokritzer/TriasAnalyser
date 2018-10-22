package de.dhbw.studienarbeit.data.helper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DataManagerTest
{
	boolean isUpdated = false;
	Date nextUpdate;
	final DataModel data = new DataModel()
	{
		@Override
		public void updateData(int attempts) throws IOException
		{
			isUpdated = true;
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
	void testUpdateIfTimeIsOver() throws Exception
	{
		nextUpdate = new Date(); // now

		List<DataModel> models = new ArrayList<>();
		models.add(data);

		final DataManager manager = new DataManager(models, Updaterate.UPDATE_EVERY_MINUTE);
		manager.updateAndSave(models);

		assertTrue(isUpdated);
	}

	@Test
	void testUpdateIfTimeIsNotOver() throws Exception
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 5);
		nextUpdate = c.getTime();

		List<DataModel> models = new ArrayList<>();
		models.add(data);

		final DataManager manager = new DataManager(models, Updaterate.UPDATE_EVERY_MINUTE);
		manager.updateAndSave(models);

		assertFalse(isUpdated);
	}
}
