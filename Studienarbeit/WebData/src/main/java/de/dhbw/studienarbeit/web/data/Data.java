package de.dhbw.studienarbeit.web.data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DelayCloudsDB;
import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;
import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;
import de.dhbw.studienarbeit.data.reader.database.DelayStationNeighbourDB;
import de.dhbw.studienarbeit.data.reader.database.DelayTempDB;
import de.dhbw.studienarbeit.data.reader.database.DelayVehicleTypeDB;
import de.dhbw.studienarbeit.data.reader.database.DelayWeatherTextDB;
import de.dhbw.studienarbeit.web.data.counts.Counts;
import de.dhbw.studienarbeit.web.data.counts.CountsWO;
import de.dhbw.studienarbeit.web.data.line.DelayLineWO;
import de.dhbw.studienarbeit.web.data.station.DelayStationNeighbourWO;
import de.dhbw.studienarbeit.web.data.station.DelayStationWO;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.vehicletype.DelayVehicleTypeWO;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelayCloudsCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelayCloudsWO;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelayTemperatureCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelayTemperatureWO;
import de.dhbw.studienarbeit.web.data.weather.text.DelayWeatherTextWO;

public class Data
{
	private final CountsWO countsWO;

	private final DelayStationWO delaysStationWO;
	private final DelayLineWO delayLineWO;
	private final DelayVehicleTypeWO delaysVehicleTypeWO;

	private final DelayTemperatureWO delayTemperatureWO;
	private final DelayTemperatureCorrelationCoefficientWO delayTemperatureCorrelationCoefficientWO;
	private final DelayCloudsWO delayCloudsWO;
	private final DelayCloudsCorrelationCoefficientWO delayCloudsCorrelationCoefficientWO;
	private final DelayWeatherTextWO delayWeatherTextWO;

	private final DelayStationNeighbourWO stationNeighbourWO;

	private static final Data INSTANCE = new Data();

	private Data()
	{
		final Optional<DataUpdater> updater = Optional.of(DataUpdater.getInstance());

		countsWO = new CountsWO(updater);

		delaysStationWO = new DelayStationWO(updater);
		delayLineWO = new DelayLineWO(updater);
		delaysVehicleTypeWO = new DelayVehicleTypeWO(updater);

		delayTemperatureWO = new DelayTemperatureWO(updater);
		delayTemperatureCorrelationCoefficientWO = new DelayTemperatureCorrelationCoefficientWO(updater);

		delayCloudsWO = new DelayCloudsWO(updater);
		delayCloudsCorrelationCoefficientWO = new DelayCloudsCorrelationCoefficientWO(updater);

		delayWeatherTextWO = new DelayWeatherTextWO(updater);

		stationNeighbourWO = new DelayStationNeighbourWO(updater);
	}

	public static Data getInstance()
	{
		return INSTANCE;
	}

	public static final CountsWO getCountsWO()
	{
		return getInstance().countsWO;
	}

	public static final DelayStationWO getDelaysStationWO()
	{
		return getInstance().delaysStationWO;
	}

	public static final DelayLineWO getDelayLineWO()
	{
		return getInstance().delayLineWO;
	}

	public static final DelayVehicleTypeWO getDelaysVehicleTypeWO()
	{
		return getInstance().delaysVehicleTypeWO;
	}

	public static final DelayTemperatureWO getDelayTemperatureWO()
	{
		return getInstance().delayTemperatureWO;
	}

	public static final DelayTemperatureCorrelationCoefficientWO getDelayTemperatureCorrelationCoefficientWO()
	{
		return getInstance().delayTemperatureCorrelationCoefficientWO;
	}

	public static final DelayCloudsWO getDelayCloudsWO()
	{
		return getInstance().delayCloudsWO;
	}

	public static final DelayCloudsCorrelationCoefficientWO getDelayCloudsCorrelationCoefficientWO()
	{
		return getInstance().delayCloudsCorrelationCoefficientWO;
	}

	public static final DelayWeatherTextWO getDelayWeatherTextWO()
	{
		return getInstance().delayWeatherTextWO;
	}

	public static final DelayStationNeighbourWO getStationNeighbourWO()
	{
		return getInstance().stationNeighbourWO;
	}

	/**
	 * @deprecated use WO instead.
	 */
	@Deprecated
	public static List<DelayLineDB> getDelaysLine()
	{
		return getInstance().delayLineWO.getList();
	}

	/**
	 * @deprecated use WO instead.
	 */
	@Deprecated
	public static Date getDelaysLineLastUpdate()
	{
		return getInstance().delayLineWO.getLastUpdated();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static List<DelayTempDB> getDelaysTemperature()
	{
		return getInstance().delayTemperatureWO.getList();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static Date getDelaysTemperatureLastUpdate()
	{
		return getInstance().delayTemperatureWO.getLastUpdated();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static List<Counts> getCounts()
	{
		return getInstance().countsWO.getData();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static List<DelayStationNeighbourDB> getNeighbours()
	{
		return getInstance().stationNeighbourWO.getList();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static Date getNeighboursLastUpdate()
	{
		return getInstance().stationNeighbourWO.getLastUpdated();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static List<DelayWeatherTextDB> getDelaysWeatherText()
	{
		return getInstance().delayWeatherTextWO.getList();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static Date getDelaysWeatherTextLastUpdate()
	{
		return getInstance().delayWeatherTextWO.getLastUpdated();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static List<DelayVehicleTypeDB> getDelaysVehicleType()
	{
		return getInstance().delaysVehicleTypeWO.getList();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static Date getDelaysVehicleTypeLastUpdate()
	{
		return getInstance().delaysVehicleTypeWO.getLastUpdated();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static double getDelaysTemperatureCorrelationCoefficient()
	{
		return getInstance().delayTemperatureCorrelationCoefficientWO.getData();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static Date getDelaysTemperatureCorrelationCoefficientLastUpdate()
	{
		return getInstance().delayTemperatureCorrelationCoefficientWO.getLastUpdated();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static double getDelaysCloudsCorrelationCoefficient()
	{
		return getInstance().delayCloudsCorrelationCoefficientWO.getData();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static Date getDelaysCloudsCorrelationCoefficientLastUpdate()
	{
		return getInstance().delayCloudsCorrelationCoefficientWO.getLastUpdated();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static List<DelayCloudsDB> getDelaysClouds()
	{
		return getInstance().delayCloudsWO.getList();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static List<DelayStationDB> getDelaysStation()
	{
		return getInstance().delaysStationWO.getList();
	}

	/**
	 * @deprecated use WO instead.
	 */
	public static Date getDelaysStationLastUpdate()
	{
		return getInstance().delaysStationWO.getLastUpdated();
	}
}
