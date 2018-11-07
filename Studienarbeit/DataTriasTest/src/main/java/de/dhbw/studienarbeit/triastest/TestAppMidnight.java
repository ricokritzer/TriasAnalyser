package de.dhbw.studienarbeit.triastest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableApi;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.trias.Station;

public class TestAppMidnight
{
	private static final Logger LOGGER = Logger.getLogger(TestAppMidnight.class.getName());

	public static void main(String[] args) throws ParseException, IOException
	{
		final ApiKey key = new DatabaseTableApi().selectApisByName("kvv").get(0);
		final Station station = new Station("de:08212:1", "Marktplatz", 0.0, 0.0, "kvv");

		final Date date = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").parse("2018-11-08 00-01-00");
		LOGGER.log(Level.INFO, station.getName() + " is scheduled to update at " + date.toString());

		final Timer timer = new Timer();
		timer.schedule(new MyTimerTask(() -> update(station, key)), date);
	}

	private static void update(Station station, ApiKey key)
	{
		try
		{
			station.updateAndSaveData(key);
			LOGGER.log(Level.INFO, station.getName() + " updated.");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.INFO, "IOException thrown.", e);
		}
	}
}