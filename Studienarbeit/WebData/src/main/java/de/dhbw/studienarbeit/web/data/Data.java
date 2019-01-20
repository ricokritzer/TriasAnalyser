package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.Count;
import de.dhbw.studienarbeit.data.reader.database.DelayCloudsDB;
import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;
import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;
import de.dhbw.studienarbeit.data.reader.database.DelayTempDB;
import de.dhbw.studienarbeit.data.reader.database.DelayVehicleTypeDB;
import de.dhbw.studienarbeit.data.reader.database.DelayWeatherTextDB;
import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;

public class Data
{
	private static final Logger LOGGER = Logger.getLogger(Data.class.getName());
	private static final int FIVE_MINUTES = 5 * 60;
	private static final int ONE_HOUR = 60 * 60;
	private static final int THREE_HOURS = 3 * 60 * 60;
	private static final int ONE_A_DAY = 24 * 60 * 60;
	private static final int MAX_COUNT_ITEMS = 10;

	private static List<StationNeighbourDB> neighbours = new ArrayList<>();
	private static Date neighboursLastUpdate = new Date(0);

	private static List<DelayStationDB> delaysStation = new ArrayList<>();
	private static Date delaysStationLastUpdate = new Date(0);

	private static List<DelayLineDB> delaysLine = new ArrayList<>();
	private static Date delaysLineLastUpdate = new Date(0);

	private static List<DelayTempDB> delaysTemperature = new ArrayList<>();
	private static Date delaysTemperatureLastUpdate = new Date(0);

	private static List<DelayWeatherTextDB> delaysWeatherText = new ArrayList<>();
	private static Date delaysWeatherTextLastUpdate = new Date(0);

	private static List<DelayCloudsDB> delaysClouds = new ArrayList<>();
	private static Date delaysCloudsLastUpdate = new Date(0);

	private static List<DelayVehicleTypeDB> delaysVehicleType = new ArrayList<>();
	private static Date delaysVehicleTypeLastUpdate = new Date(0);

	private static List<Counts> counts = new ArrayList<>();

	private static final Data data = new Data();

	public static Data getInstance()
	{
		return data;
	}

	private Data()
	{
		DataUpdater.scheduleUpdate(Data::updateDelaysLine, ONE_HOUR, "DelaysLine");
		DataUpdater.scheduleUpdate(Data::updateDelaysVehicleType, ONE_HOUR, "DelaysVehicleType");
		DataUpdater.scheduleUpdate(Data::updateDelaysStation, ONE_HOUR, "DelaysStation");
		DataUpdater.scheduleUpdate(Data::updateNeighbours, ONE_A_DAY, "Neighbours");
		DataUpdater.scheduleUpdate(Data::updateCount, FIVE_MINUTES, "Count");
		DataUpdater.scheduleUpdate(Data::updateDelaysTemperature, THREE_HOURS, "DelaysTemperature");
		DataUpdater.scheduleUpdate(Data::updateDelaysClouds, THREE_HOURS, "DelaysClouds");
		DataUpdater.scheduleUpdate(Data::updateDelaysWeatherText, THREE_HOURS, "DelaysWeatherText");
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
	}

	private static void reduceToTen(List<? extends Object> list)
	{
		while (list.size() > MAX_COUNT_ITEMS)
		{
			list.remove(MAX_COUNT_ITEMS);
		}
	}

	private static void updateDelaysTemperature()
	{
		try
		{
			delaysTemperature = DelayTempDB.getDelays();
			delaysTemperatureLastUpdate = new Date();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delaysTemperatur", e);
		}
	}

	private static void updateDelaysWeatherText()
	{
		try
		{
			delaysWeatherText = DelayWeatherTextDB.getDelays();
			delaysWeatherTextLastUpdate = new Date();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delayWeatherText", e);
		}
	}

	private static void updateDelaysVehicleType()
	{
		try
		{
			delaysVehicleType = DelayVehicleTypeDB.getDelays();
			delaysVehicleTypeLastUpdate = new Date();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delayVehicleType", e);
		}
	}

	private static void updateDelaysLine()
	{
		try
		{
			delaysLine = DelayLineDB.getDelays();
			delaysLineLastUpdate = new Date();
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
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delaysStation", e);
		}
	}

	private static void updateNeighbours()
	{
		try
		{
			neighbours = StationNeighbourDB.getStationNeighbours();
			neighboursLastUpdate = new Date();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update StationNeighbourDB", e);
		}
	}

	private static void updateDelaysClouds()
	{
		try
		{
			delaysClouds = DelayCloudsDB.getDelays();
			delaysCloudsLastUpdate = new Date();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delaysClouds", e);
		}
	}

	public static List<DelayCloudsDB> getDelaysClouds()
	{
		return delaysClouds;
	}

	public static Date getDelaysCloudsLastUpdate()
	{
		return delaysCloudsLastUpdate;
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

	public static List<DelayTempDB> getDelaysTemperature()
	{
		return delaysTemperature;
	}

	public static Date getDelaysTemperatureLastUpdate()
	{
		return delaysTemperatureLastUpdate;
	}

	public static List<Counts> getCounts()
	{
		return counts;
	}

	public static List<StationNeighbourDB> getNeighbours()
	{
		return neighbours;
	}

	public static Date getNeighboursLastUpdate()
	{
		return neighboursLastUpdate;
	}

	public static List<DelayWeatherTextDB> getDelaysWeatherText()
	{
		return delaysWeatherText;
	}

	public static Date getDelaysWeatherTextLastUpdate()
	{
		return delaysWeatherTextLastUpdate;
	}

	public static List<DelayVehicleTypeDB> getDelaysVehicleType()
	{
		return delaysVehicleType;
	}

	public static Date getDelaysVehicleTypeLastUpdate()
	{
		return delaysVehicleTypeLastUpdate;
	}
}
