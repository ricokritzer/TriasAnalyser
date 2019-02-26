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

public class Data
{
	private StationNeighbours stationNeighbours = new StationNeighbours(Optional.of(DataUpdater.getInstance()));

	private DelaysStation delaysStation = new DelaysStation();
	private DelaysLine delaysLine = new DelaysLine();
	private DelaysVehicleType delaysVehicleType = new DelaysVehicleType();

	private DelaysTemperature delaysTemperature = new DelaysTemperature();
	private DelaysTemperatureCorrelationCoefficient delaysTemperatureCorrelationCoefficient = new DelaysTemperatureCorrelationCoefficient();

	private DelaysClouds delaysClouds = new DelaysClouds();
	private DelaysCloudsCorrelationCoefficient delaysCloudsCorrelationCoefficient = new DelaysCloudsCorrelationCoefficient();

	private DelaysWeatherText delaysWeatherText = new DelaysWeatherText();

	private CountList counts = new CountList();

	private static Data instance = new Data();

	private Data()
	{}

	public static Data getInstance()
	{
		return instance;
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
