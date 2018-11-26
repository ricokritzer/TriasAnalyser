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
	private static final int FIVE_MINUTES = 5 * 60;
	private static final int FIFTEEN_MINUTES = 15 * 60;
	private static final int MAX_COUNT_ITEMS = 10;

	private static List<DelayStationDB> delaysStation = new ArrayList<>();
	private static Date delaysStationLastUpdate = new Date(0);

	private static List<DelayLineDB> delaysLine = new ArrayList<>();
	private static Date delaysLineLastUpdate = new Date(0);

	private static List<Counts> counts = new ArrayList<>();

	private static final Data data = new Data();

	public static Data getInstance()
	{
		return data;
	}

	private Data()
	{
		DataUpdater.scheduleUpdate(Data::updateDelaysLine, FIFTEEN_MINUTES, "DelaysLine");
		DataUpdater.scheduleUpdate(Data::updateDelaysStation, FIFTEEN_MINUTES, "DelaysStation");
		DataUpdater.scheduleUpdate(Data::updateCount, FIVE_MINUTES, "Count");
	}

	private static void updateCount()
	{
		Count countStations = Count.countStations();
		Count countObservedStations = Count.countObservedStations();
		Count countStationsWithRealtimeData = Count.countStationsWithRealtimeData();
		Count countLines = Count.countLines();
		Count countStops = Count.countStops();
		Count countWeathers = Count.countWeather();
		Count countOperators = Count.countOperators();
		Date lastUpdate = new Date();

		counts.add(0, new Counts(countStations, countObservedStations, countStationsWithRealtimeData, countLines,
				countStops, countWeathers, countOperators, lastUpdate));
		reduceToTen(counts);

		LOGGER.log(Level.INFO, "Count updated.");
	}

	private static void reduceToTen(List<? extends Object> list)
	{
		while (list.size() > MAX_COUNT_ITEMS)
		{
			list.remove(MAX_COUNT_ITEMS);
		}
	}

	private static void updateDelaysLine()
	{
		try
		{
			delaysLine = DelayLineDB.getDelays();
			delaysLineLastUpdate = new Date();
			LOGGER.log(Level.INFO, "DelayLine updated.");
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
			delaysStationLastUpdate = new Date();
			LOGGER.log(Level.INFO, "DelayStation updated.");
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

	public static List<Counts> getCounts()
	{
		return counts;
	}
}
