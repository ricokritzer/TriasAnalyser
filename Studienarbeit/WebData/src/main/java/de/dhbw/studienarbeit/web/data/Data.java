package de.dhbw.studienarbeit.web.data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DelayCloudsDB;
import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;
import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;
import de.dhbw.studienarbeit.data.reader.database.DelayTempDB;
import de.dhbw.studienarbeit.data.reader.database.DelayVehicleTypeDB;
import de.dhbw.studienarbeit.data.reader.database.DelayWeatherTextDB;
import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;
import de.dhbw.studienarbeit.web.data.counts.CountList;
import de.dhbw.studienarbeit.web.data.counts.Counts;
import de.dhbw.studienarbeit.web.data.line.DelayLineWO;
import de.dhbw.studienarbeit.web.data.station.DelaysStation;
import de.dhbw.studienarbeit.web.data.station.StationNeighbours;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.vehicletype.DelaysVehicleType;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelaysClouds;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelaysCloudsCorrelationCoefficient;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelaysTemperature;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelaysTemperatureCorrelationCoefficient;
import de.dhbw.studienarbeit.web.data.weather.text.DelaysWeatherText;

public class Data
{
	private final CountList counts;

	private final DelaysStation delaysStation;
	private final DelayLineWO delayLineWO;
	private final DelaysVehicleType delaysVehicleType;

	private final DelaysTemperature delaysTemperature;
	private final DelaysTemperatureCorrelationCoefficient delaysTemperatureCorrelationCoefficient;
	private final DelaysClouds delaysClouds;
	private final DelaysCloudsCorrelationCoefficient delaysCloudsCorrelationCoefficient;
	private final DelaysWeatherText delaysWeatherText;

	private final StationNeighbours stationNeighbours;

	private static final Data INSTANCE = new Data();

	private Data()
	{
		final Optional<DataUpdater> updater = Optional.of(DataUpdater.getInstance());

		counts = new CountList(updater);

		delaysStation = new DelaysStation(updater);
		delayLineWO = new DelayLineWO(updater);
		delaysVehicleType = new DelaysVehicleType(updater);

		delaysTemperature = new DelaysTemperature(updater);
		delaysTemperatureCorrelationCoefficient = new DelaysTemperatureCorrelationCoefficient(updater);

		delaysClouds = new DelaysClouds(updater);
		delaysCloudsCorrelationCoefficient = new DelaysCloudsCorrelationCoefficient(updater);

		delaysWeatherText = new DelaysWeatherText(updater);

		stationNeighbours = new StationNeighbours(updater);
	}

	public static Data getInstance()
	{
		return INSTANCE;
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

	/**
	 * @deprecated use {@link #getDelayLineWO()} instead.
	 */
	@Deprecated
	public static List<DelayLineDB> getDelaysLine()
	{
		return getInstance().delayLineWO.getList();
	}

	/**
	 * @deprecated use {@link #getDelayLineWO()} instead.
	 */
	@Deprecated
	public static Date getDelaysLineLastUpdate()
	{
		return getInstance().delayLineWO.getLastUpdated();
	}

	public static DelayLineWO getDelayLineWO()
	{
		return getInstance().delayLineWO;
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
		return getInstance().counts.getData();
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
		return getInstance().delaysWeatherText.getData();
	}

	public static Date getDelaysWeatherTextLastUpdate()
	{
		return getInstance().delaysWeatherText.getLastUpdated();
	}

	public static List<DelayVehicleTypeDB> getDelaysVehicleType()
	{
		return getInstance().delaysVehicleType.getData();
	}

	public static Date getDelaysVehicleTypeLastUpdate()
	{
		return getInstance().delaysVehicleType.getLastUpdated();
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
