package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.Count;
import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;
import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;

public class Data
{
	private static final Logger LOGGER = Logger.getLogger(Data.class.getName());

	private static List<DelayStationDB> delaysStation = new ArrayList<>();
	private static List<DelayLineDB> delaysLine = new ArrayList<>();
	private static Count countStations = Count.UNABLE_TO_COUNT;
	private static Count countLines = Count.UNABLE_TO_COUNT;
	private static Count countOperators = Count.UNABLE_TO_COUNT;
	private static Count countStops = Count.UNABLE_TO_COUNT;
	private static Date lastUpdate = new Date(0);

	private static Data data = new Data();

	public static Data getInstance()
	{
		return data;
	}

	private Data()
	{
		update();
		new DataUpdater();
	}

	public void update()
	{
		updateDelaysStation();
		updateDelaysLine();
		countStations = Count.countStations();
		countLines = Count.countLines();
		countOperators = Count.UNABLE_TO_COUNT;
		countStops = Count.countStops();

		lastUpdate = new Date();

		delaysLine.add(new DelayLineDB(0, 0, "Auto", "in Richtung Zukunft"));

		LOGGER.log(Level.INFO, "Data updated.");
	}

	private static void updateDelaysLine()
	{
		try
		{
			delaysLine = DelayLineDB.getDelays();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delaysLine", e);
		}
	}

	private static void updateDelaysStation()
	{
		try
		{
			delaysStation = DelayStationDB.getDelays();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delaysStation", e);
		}
	}

	public static List<DelayStationDB> getDelaysStation()
	{
		return delaysStation;
	}

	public static List<DelayLineDB> getDelaysLine()
	{
		return delaysLine;
	}

	public static Count getCountStations()
	{
		return countStations;
	}

	public static Count getCountLines()
	{
		return countLines;
	}

	public static Count getCountOperators()
	{
		return countOperators;
	}

	public static Count getCountStops()
	{
		return countStops;
	}

	public static Date getLastUpdate()
	{
		return lastUpdate;
	}
}
