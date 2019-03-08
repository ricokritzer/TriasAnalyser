package de.dhbw.studienarbeit.web.data;

import java.util.Optional;

import de.dhbw.studienarbeit.web.data.counts.CountsWO;
import de.dhbw.studienarbeit.web.data.line.DelayLineWO;
import de.dhbw.studienarbeit.web.data.station.DelayStationNeighbourWO;
import de.dhbw.studienarbeit.web.data.station.DelayStationWO;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.vehicletype.DelayVehicleTypeWO;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelayCloudsCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelayCloudsWO;
import de.dhbw.studienarbeit.web.data.weather.humidity.DelayHumidityCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.humidity.DelayHumidityWO;
import de.dhbw.studienarbeit.web.data.weather.pressure.DelayPressureCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.pressure.DelayPressureWO;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelayTemperatureCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelayTemperatureWO;
import de.dhbw.studienarbeit.web.data.weather.text.DelayWeatherTextWO;
import de.dhbw.studienarbeit.web.data.weather.wind.DelayWindCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.wind.DelayWindWO;

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
	private final DelayWindWO delayWindWO;
	private final DelayWindCorrelationCoefficientWO delayWindCorrelationCoefficientWO;
	private final DelayHumidityWO delayHumidityWO;
	private final DelayHumidityCorrelationCoefficientWO delayHumidityCorrelationCoefficientWO;
	private final DelayPressureWO delayPressureWO;
	private final DelayPressureCorrelationCoefficientWO delayPressureCorrelationCoefficientWO;

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

		delayWindWO = new DelayWindWO(updater);
		delayWindCorrelationCoefficientWO = new DelayWindCorrelationCoefficientWO(updater);

		delayHumidityWO = new DelayHumidityWO(updater);
		delayHumidityCorrelationCoefficientWO = new DelayHumidityCorrelationCoefficientWO(updater);

		delayPressureWO = new DelayPressureWO(updater);
		delayPressureCorrelationCoefficientWO = new DelayPressureCorrelationCoefficientWO(updater);

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

	public static final DelayWindWO getDelayWindWO()
	{
		return getInstance().delayWindWO;
	}

	public static final DelayWindCorrelationCoefficientWO getDelayWindCorrelationCoefficientWO()
	{
		return getInstance().delayWindCorrelationCoefficientWO;
	}

	public static final DelayHumidityWO getDelayHumidityWO()
	{
		return getInstance().delayHumidityWO;
	}

	public static final DelayHumidityCorrelationCoefficientWO getDelayHumidityCorrelationCoefficientWO()
	{
		return getInstance().delayHumidityCorrelationCoefficientWO;
	}

	public static final DelayPressureWO getDelayPressureWO()
	{
		return getInstance().delayPressureWO;
	}

	public static final DelayPressureCorrelationCoefficientWO getDelayPressureCorrelationCoefficientWO()
	{
		return getInstance().delayPressureCorrelationCoefficientWO;
	}
}
