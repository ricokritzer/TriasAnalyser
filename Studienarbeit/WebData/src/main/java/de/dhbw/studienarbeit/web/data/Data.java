package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
	private static final int MAX_COUNT_ITEMS = 10;

	private static Optional<Data> instance = Optional.empty();

	private StationNeighbours stationNeighbours = new StationNeighbours();

	private DelaysStation delaysStation = new DelaysStation();
	private DelaysLine delaysLine = new DelaysLine();

	private DelaysTemperature delaysTemperature = new DelaysTemperature();
	private DelaysTemperatureCorrelationCoefficient delaysTemperatureCorrelationCoefficient = new DelaysTemperatureCorrelationCoefficient();

	private DelaysClouds delaysClouds = new DelaysClouds();
	private DelaysCloudsCorrelationCoefficient delaysCloudsCorrelationCoefficient = new DelaysCloudsCorrelationCoefficient();

	private List<DelayWeatherTextDB> delaysWeatherText = new ArrayList<>();
	private Date delaysWeatherTextLastUpdate = new Date(0);

	private List<DelayVehicleTypeDB> delaysVehicleType = new ArrayList<>();
	private Date delaysVehicleTypeLastUpdate = new Date(0);

	private List<Counts> counts = new ArrayList<>();

	public static Data getInstance()
	{
		if (!instance.isPresent())
		{
			instance = Optional.ofNullable(new Data());
		}
		return instance.orElse(new Data());
	}

	public static void main(String[] args)
	{
		System.out.println(getNeighbours().size());
	}

	private Data()
	{
		new Thread(this::updateFirstTime).start();

		DataUpdater.scheduleUpdate(this::updateDelaysVehicleType, ONE_HOUR, "DelaysVehicleType");
		DataUpdater.scheduleUpdate(this::updateCount, FIVE_MINUTES, "Count");
		DataUpdater.scheduleUpdate(this::updateDelaysWeatherText, THREE_HOURS, "DelaysWeatherText");
	}

	private void updateFirstTime()
	{
		watchTime(this::updateCount);
		watchTime(this::updateDelaysVehicleType);
		watchTime(this::updateDelaysWeatherText);

		LOGGER.log(Level.INFO, "Everthing updated for the first time.");
	}

	private void watchTime(Runnable r)
	{
		final Date start = new Date();
		r.run();
		final Date end = new Date();
		final long time = end.getTime() - start.getTime();
		LOGGER.log(Level.INFO, "Duration (ms): " + time);
	}

	private void updateCount()
	{
		Count countStations = Count.countStations();
		Count countObservedStations = Count.countObservedStations();
		Count countStationsWithRealtimeData = Count.countStationsWithRealtimeData();
		Count countLines = Count.countLines();
		Count countStops = Count.countStops();
		Count countWeathers = Count.countWeather();
		Count countOperators = Count.countObservedOperators();
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

	private void updateDelaysWeatherText()
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

	private void updateDelaysVehicleType()
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

	public static List<DelayCloudsDB> getDelaysClouds()
	{
		return getInstance().delaysClouds.getData();
	}

	public static Date getDelaysCloudsLastUpdate()
	{
		return getInstance().delaysClouds.getLastUpdated();
	}

	public static List<DelayStationDB> getDelaysStation()
	{
		return getInstance().delaysStation.getData();
	}

	public static Date getDelaysStationLastUpdate()
	{
		return getInstance().delaysStation.getLastUpdated();
	}

	public static List<DelayLineDB> getDelaysLine()
	{
		return getInstance().delaysLine.getData();
	}

	public static Date getDelaysLineLastUpdate()
	{
		return getInstance().delaysLine.getLastUpdated();
	}

	public static List<DelayTempDB> getDelaysTemperature()
	{
		return getInstance().delaysTemperature.getData();
	}

	public static Date getDelaysTemperatureLastUpdate()
	{
		return getInstance().delaysTemperature.getLastUpdated();
	}

	public static List<Counts> getCounts()
	{
		return getInstance().counts;
	}

	public static List<StationNeighbourDB> getNeighbours()
	{
		return getInstance().stationNeighbours.getData();
	}

	public static Date getNeighboursLastUpdate()
	{
		return getInstance().stationNeighbours.getLastUpdated();
	}

	public static List<DelayWeatherTextDB> getDelaysWeatherText()
	{
		return getInstance().delaysWeatherText;
	}

	public static Date getDelaysWeatherTextLastUpdate()
	{
		return getInstance().delaysWeatherTextLastUpdate;
	}

	public static List<DelayVehicleTypeDB> getDelaysVehicleType()
	{
		return getInstance().delaysVehicleType;
	}

	public static Date getDelaysVehicleTypeLastUpdate()
	{
		return getInstance().delaysVehicleTypeLastUpdate;
	}

	public static double getDelaysTemperatureCorrelationCoefficient()
	{
		return getInstance().delaysTemperatureCorrelationCoefficient.getData();
	}

	public static Date getDelaysTemperatureCorrelationCoefficientLastUpdate()
	{
		return getInstance().delaysTemperatureCorrelationCoefficient.getLastUpdated();
	}

	public static double getDelaysCloudsCorrelationCoefficient()
	{
		return getInstance().delaysCloudsCorrelationCoefficient.getData();
	}

	public static Date getDelaysCloudsCorrelationCoefficientLastUpdate()
	{
		return getInstance().delaysCloudsCorrelationCoefficient.getLastUpdated();
	}
}
