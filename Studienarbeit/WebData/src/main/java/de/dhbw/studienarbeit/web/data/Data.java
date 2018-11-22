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

	private static final int MAX_COUNT_ITEMS = 10;

	private static List<DelayStationDB> delaysStation = new ArrayList<>();
	private static Date delaysStationLastUpdate = new Date(0);

	private static List<DelayLineDB> delaysLine = new ArrayList<>();
	private static Date delaysLineLastUpdate = new Date(0);

	private static List<Count> countStations = new ArrayList<>();
	private static List<Count> countLines = new ArrayList<>();
	private static List<Count> countOperators = new ArrayList<>();
	private static List<Count> countStops = new ArrayList<>();
	private static List<Date> countUpdates = new ArrayList<>();

	private static Data data = new Data();

	public static Data getInstance()
	{
		return data;
	}

	private Data()
	{
		DataUpdater.scheduleUpdate(Data::updateDelaysLine, 300, "DelaysLine");
		DataUpdater.scheduleUpdate(Data::updateDelaysStation, 300, "DelaysStation");
		DataUpdater.scheduleUpdate(Data::updateCount, 60, "Count");
	}

	private static void updateCount()
	{
		countStations.add(Count.countStations());
		reduceToTen(countStations);

		countLines.add(Count.countLines());
		reduceToTen(countLines);

		countOperators.add(Count.UNABLE_TO_COUNT);
		reduceToTen(countOperators);

		countStops.add(Count.countStops());
		reduceToTen(countStops);

		countUpdates.add(new Date());
		reduceToTen(countUpdates);
	}

	private static void reduceToTen(List<? extends Object> list)
	{
		while (list.size() > MAX_COUNT_ITEMS)
		{
			list.remove(0);
		}
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

	public static Date getDelaysStationLastUpdate()
	{
		return delaysStationLastUpdate;
	}

	public static List<DelayLineDB> getDelaysLine()
	{
		return delaysLine;
	}

	public static Date getDelaysLineLastUpdate()
	{
		return delaysLineLastUpdate;
	}

	public static List<Count> getCountStations()
	{
		return countStations;
	}

	public static List<Count> getCountLines()
	{
		return countLines;
	}

	public static List<Count> getCountOperators()
	{
		return countOperators;
	}

	public static List<Count> getCountStops()
	{
		return countStops;
	}

	public static List<Date> getCountUpdates()
	{
		return countUpdates;
	}
}
