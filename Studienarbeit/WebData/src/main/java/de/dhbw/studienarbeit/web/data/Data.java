package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.DelayLineData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequest;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestDB;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestFixedTime;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestFixedTimeDB;
import de.dhbw.studienarbeit.data.reader.data.request.LinesAtStationDB;
import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituationData;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbourData;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.DelayTimeSpanData;
import de.dhbw.studienarbeit.data.reader.data.time.DelayWeekdayData;
import de.dhbw.studienarbeit.data.reader.data.vehicletype.DelayVehicleTypeData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudsData;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityData;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureData;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureData;
import de.dhbw.studienarbeit.data.reader.data.weather.text.DelayWeatherTextData;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindData;
import de.dhbw.studienarbeit.web.data.counts.CountLinesWO;
import de.dhbw.studienarbeit.web.data.counts.CountObservedOperatorsWO;
import de.dhbw.studienarbeit.web.data.counts.CountObservedStationsWO;
import de.dhbw.studienarbeit.web.data.counts.CountStationsWO;
import de.dhbw.studienarbeit.web.data.counts.CountStationsWithRealtimeDataWO;
import de.dhbw.studienarbeit.web.data.counts.CountStopsWO;
import de.dhbw.studienarbeit.web.data.counts.CountWO;
import de.dhbw.studienarbeit.web.data.counts.CountWeathersWO;
import de.dhbw.studienarbeit.web.data.line.DelayLineWO;
import de.dhbw.studienarbeit.web.data.situation.DelaySituationWO;
import de.dhbw.studienarbeit.web.data.station.DelayStationNeighbourWO;
import de.dhbw.studienarbeit.web.data.station.DelayStationWO;
import de.dhbw.studienarbeit.web.data.time.DelayTimeSpanWO;
import de.dhbw.studienarbeit.web.data.time.DelayWeekdayWO;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.vehicletype.DelayVehicleTypeWO;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelayCloudsCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.clouds.DelayCloudsWO;
import de.dhbw.studienarbeit.web.data.weather.humidity.DelayHumidityCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.humidity.DelayHumidityWO;
import de.dhbw.studienarbeit.web.data.weather.pressure.DelayPressureCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.pressure.DelayPressureWO;
import de.dhbw.studienarbeit.web.data.weather.symbol.DelayWeatherSymbolWO;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelayTemperatureCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.temperature.DelayTemperatureWO;
import de.dhbw.studienarbeit.web.data.weather.text.DelayWeatherTextWO;
import de.dhbw.studienarbeit.web.data.weather.wind.DelayWindCorrelationCoefficientWO;
import de.dhbw.studienarbeit.web.data.weather.wind.DelayWindWO;

public class Data
{
	private final CountLinesWO countLinesWO;
	private final CountObservedStationsWO countObservedStationsWO;
	private final CountObservedOperatorsWO countObservedOperatorsWO;
	private final CountStationsWithRealtimeDataWO countStationsWithRealtimeDataWO;
	private final CountStationsWO countStationsWO;
	private final CountStopsWO countStopsWO;
	private final CountWeathersWO countWeathersWO;

	private final DelayStationWO delaysStationWO;
	private final DelayLineWO delayLineWO;
	private final DelayVehicleTypeWO delayVehicleTypeWO;
	private final DelayWeekdayWO delayWeekdayWO;
	private final DelayTimeSpanWO delayTimeSpanWO;

	private final DelayWeatherTextWO delayWeatherTextWO;
	private final DelayWeatherSymbolWO delayWeatherSymbolWO;

	private final DelayTemperatureWO delayTemperatureWO;
	private final DelayTemperatureCorrelationCoefficientWO delayTemperatureCorrelationCoefficientWO;
	private final DelayCloudsWO delayCloudsWO;
	private final DelayCloudsCorrelationCoefficientWO delayCloudsCorrelationCoefficientWO;
	private final DelayWindWO delayWindWO;
	private final DelayWindCorrelationCoefficientWO delayWindCorrelationCoefficientWO;
	private final DelayHumidityWO delayHumidityWO;
	private final DelayHumidityCorrelationCoefficientWO delayHumidityCorrelationCoefficientWO;
	private final DelayPressureWO delayPressureWO;
	private final DelayPressureCorrelationCoefficientWO delayPressureCorrelationCoefficientWO;

	private final DelayStationNeighbourWO stationNeighbourWO;

	private final DelaySituationWO delaySituationWO;

	private static final Data INSTANCE = new Data();

	private Data()
	{
		final Optional<DataUpdater> updater = Optional.of(DataUpdater.getInstance());

		countLinesWO = new CountLinesWO(updater);
		countObservedStationsWO = new CountObservedStationsWO(updater);
		countObservedOperatorsWO = new CountObservedOperatorsWO(updater);
		countStationsWithRealtimeDataWO = new CountStationsWithRealtimeDataWO(updater);
		countStationsWO = new CountStationsWO(updater);
		countStopsWO = new CountStopsWO(updater);
		countWeathersWO = new CountWeathersWO(updater);

		delaysStationWO = new DelayStationWO(updater);
		delayLineWO = new DelayLineWO(updater);
		delayVehicleTypeWO = new DelayVehicleTypeWO(updater);
		delayWeekdayWO = new DelayWeekdayWO(updater);
		delayTimeSpanWO = new DelayTimeSpanWO(updater);

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
		delayWeatherSymbolWO = new DelayWeatherSymbolWO(updater);

		stationNeighbourWO = new DelayStationNeighbourWO(updater);

		delaySituationWO = new DelaySituationWO(updater);
	}

	public static Data getInstance()
	{
		return INSTANCE;
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayStationWO getDelaysStationWO()
	{
		return getInstance().delaysStationWO;
	}

	public static final List<DelayStationData> getDelaysStation()
	{
		return getInstance().delaysStationWO.getData();
	}

	public static final Date getDelaysStationLastUpdate()
	{
		return getInstance().delaysStationWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayLineWO getDelayLineWO()
	{
		return getInstance().delayLineWO;
	}

	public static final List<DelayLineData> getDelaysLine()
	{
		return getInstance().delayLineWO.getData();
	}

	public static final Date getDelaysLineLastUpdated()
	{
		return getInstance().delayLineWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayVehicleTypeWO getDelaysVehicleTypeWO()
	{
		return getInstance().delayVehicleTypeWO;
	}

	public static final List<DelayVehicleTypeData> getDelaysVehicleType()
	{
		return getInstance().delayVehicleTypeWO.getData();
	}

	public static final Date getDelaysVehicleTypeLastUpdate()
	{
		return getInstance().delayVehicleTypeWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayTemperatureWO getDelayTemperatureWO()
	{
		return getInstance().delayTemperatureWO;
	}

	public static final List<DelayTemperatureData> getDelaysTemperature()
	{
		return getInstance().delayTemperatureWO.getData();
	}

	public static final Date getDelaysTemperatureLastUpdated()
	{
		return getInstance().delayTemperatureWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayTemperatureCorrelationCoefficientWO getDelayTemperatureCorrelationCoefficientWO()
	{
		return getInstance().delayTemperatureCorrelationCoefficientWO;
	}

	public static final DelayTemperatureCorrelationData getDelayTemperatureCorrelationCoefficient()
	{
		return getInstance().delayTemperatureCorrelationCoefficientWO.getCorrelation();
	}

	public static final Date getDelayTemperatureCorrelationCoefficientLastUpdated()
	{
		return getInstance().delayTemperatureCorrelationCoefficientWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayCloudsWO getDelayCloudsWO()
	{
		return getInstance().delayCloudsWO;
	}

	public static final List<DelayCloudsData> getDelaysClouds()
	{
		return getInstance().delayCloudsWO.getData();
	}

	public static final Date getDelaysCloudsLastUpdated()
	{
		return getInstance().delayCloudsWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayCloudsCorrelationCoefficientWO getDelayCloudsCorrelationCoefficientWO()
	{
		return getInstance().delayCloudsCorrelationCoefficientWO;
	}

	public static final DelayCloudCorrelationData getDelayCloudsCorrelationCoefficient()
	{
		return getInstance().delayCloudsCorrelationCoefficientWO.getCorrelation();
	}

	public static final Date getDelayCloudsCorrelationCoefficientLastUpdated()
	{
		return getInstance().delayCloudsCorrelationCoefficientWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayWeatherTextWO getDelayWeatherTextWO()
	{
		return getInstance().delayWeatherTextWO;
	}

	public static final List<DelayWeatherTextData> getDelaysWeatherText()
	{
		return getInstance().delayWeatherTextWO.getData();
	}

	public static final Date getDelaysWeatherTextLastUpdated()
	{
		return getInstance().delayWeatherTextWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayStationNeighbourWO getStationNeighbourWO()
	{
		return getInstance().stationNeighbourWO;
	}

	public static final List<DelayStationNeighbourData> getStationNeighbours()
	{
		return getInstance().stationNeighbourWO.getData();
	}

	public static final Date getStationNeighboursLastUpdated()
	{
		return getInstance().stationNeighbourWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayWindWO getDelayWindWO()
	{
		return getInstance().delayWindWO;
	}

	public static final List<DelayWindData> getDelaysWind()
	{
		return getInstance().delayWindWO.getData();
	}

	public static final Date getDelaysWindLastUpdated()
	{
		return getInstance().delayWindWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayWindCorrelationCoefficientWO getDelayWindCorrelationCoefficientWO()
	{
		return getInstance().delayWindCorrelationCoefficientWO;
	}

	public static final DelayWindCorrelationData getDelayWindCorrelationCoefficient()
	{
		return getInstance().delayWindCorrelationCoefficientWO.getCorrelation();
	}

	public static final Date getDelayWindCorrelationCoefficientLastUpdated()
	{
		return getInstance().delayWindCorrelationCoefficientWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayHumidityWO getDelayHumidityWO()
	{
		return getInstance().delayHumidityWO;
	}

	public static final List<DelayHumidityData> getDelaysHumidity()
	{
		return getInstance().delayHumidityWO.getData();
	}

	public static final Date getDelaysHumidityLastUpdated()
	{
		return getInstance().delayHumidityWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayHumidityCorrelationCoefficientWO getDelayHumidityCorrelationCoefficientWO()
	{
		return getInstance().delayHumidityCorrelationCoefficientWO;
	}

	public static final DelayHumidityCorrelationData getDelayHumidityCorrelationCoefficient()
	{
		return getInstance().delayHumidityCorrelationCoefficientWO.getCorrelation();
	}

	public static final Date getDelayHumidityCorrelationCoefficientLastUpdated()
	{
		return getInstance().delayHumidityCorrelationCoefficientWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayPressureWO getDelayPressureWO()
	{
		return getInstance().delayPressureWO;
	}

	public static final List<DelayPressureData> getDelaysPressure()
	{
		return getInstance().delayPressureWO.getData();
	}

	public static final Date getDelaysPressureLastUpdated()
	{
		return getInstance().delayPressureWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayPressureCorrelationCoefficientWO getDelayPressureCorrelationCoefficientWO()
	{
		return getInstance().delayPressureCorrelationCoefficientWO;
	}

	public static final DelayPressureCorrelationData getDelayPressureCorrelationCoefficient()
	{
		return getInstance().delayPressureCorrelationCoefficientWO.getCorrelation();
	}

	public static final Date getDelayPressureCorrelationCoefficientLastUpdated()
	{
		return getInstance().delayPressureCorrelationCoefficientWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayWeekdayWO getDelayWeekdayWO()
	{
		return getInstance().delayWeekdayWO;
	}

	public static final List<DelayWeekdayData> getDelaysWeekday()
	{
		return getInstance().delayWeekdayWO.getData();
	}

	public static final Date getDelaysWeekdayLastUpdated()
	{
		return getInstance().delayWeekdayWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayTimeSpanWO getDelayTimeSpanWO()
	{
		return getInstance().delayTimeSpanWO;
	}

	public static final List<DelayTimeSpanData> getDelaysTimeSpan()
	{
		return getInstance().delayTimeSpanWO.getData();
	}

	public static final Date getDelaysTimeSpanLastUpdated()
	{
		return getInstance().delayTimeSpanWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static final DelayWeatherSymbolWO getDelayWeatherSymbolWO()
	{
		return getInstance().delayWeatherSymbolWO;
	}

	public static final DelayWeatherSymbolWO getDelaysWeatherSymbol()
	{
		return getInstance().delayWeatherSymbolWO;
	}

	public static final DelayWeatherSymbolWO getDelaysWeatherSymbolLastUpdated()
	{
		return getInstance().delayWeatherSymbolWO;
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static CountLinesWO getCountLinesWO()
	{
		return getInstance().countLinesWO;
	}

	public static List<CountWO> getCountLines()
	{
		return getInstance().countLinesWO.getValues();
	}

	public static CountData getCountLinesLatest()
	{
		return getInstance().countLinesWO.getNewestValue();
	}

	public static Date getCountLinesLastUpdated()
	{
		return getInstance().countLinesWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static CountObservedStationsWO getCountObservedStationsWO()
	{
		return getInstance().countObservedStationsWO;
	}

	public static List<CountWO> getCountObservedStations()
	{
		return getInstance().countObservedStationsWO.getValues();
	}

	public static CountData getCountObservedStationsLatest()
	{
		return getInstance().countObservedStationsWO.getNewestValue();
	}

	public static Date getCountObservedStationsLastUpdated()
	{
		return getInstance().countObservedStationsWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static CountObservedOperatorsWO getCountObservedOperatorsWO()
	{
		return getInstance().countObservedOperatorsWO;
	}

	public static List<CountWO> getCountObservedOperators()
	{
		return getInstance().countObservedOperatorsWO.getValues();
	}

	public static CountData getCountObservedOperatorsLatest()
	{
		return getInstance().countObservedOperatorsWO.getNewestValue();
	}

	public static Date getCountObservedOperatorsLastUpdated()
	{
		return getInstance().countObservedOperatorsWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static CountStationsWithRealtimeDataWO getCountStationsWithRealtimeDataWO()
	{
		return getInstance().countStationsWithRealtimeDataWO;
	}

	public static List<CountWO> getCountStationsWithRealtimeData()
	{
		return getInstance().countStationsWithRealtimeDataWO.getValues();
	}

	public static CountData getCountStationsWithRealtimeDataLatest()
	{
		return getInstance().countStationsWithRealtimeDataWO.getNewestValue();
	}

	public static Date getCountStationsWithRealtimeDataLastUpdated()
	{
		return getInstance().countStationsWithRealtimeDataWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static CountStationsWO getCountStationsWO()
	{
		return getInstance().countStationsWO;
	}

	public static List<CountWO> getCountStations()
	{
		return getInstance().countStationsWO.getValues();
	}

	public static CountData getCountStationsLatest()
	{
		return getInstance().countStationsWO.getNewestValue();
	}

	public static Date getCountStationsLastUpdated()
	{
		return getInstance().countStationsWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static CountStopsWO getCountStopsWO()
	{
		return getInstance().countStopsWO;
	}

	public static List<CountWO> getCountStops()
	{
		return getInstance().countStopsWO.getValues();
	}

	public static CountData getCountStopsLatest()
	{
		return getInstance().countStopsWO.getNewestValue();
	}

	public static Date getCountStopsLastUpdated()
	{
		return getInstance().countStopsWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static CountWeathersWO getCountWeathersWO()
	{
		return getInstance().countWeathersWO;
	}

	public static List<CountWO> getCountWeathers()
	{
		return getInstance().countWeathersWO.getValues();
	}

	public static CountData getCountWeathersLatest()
	{
		return getInstance().countWeathersWO.getNewestValue();
	}

	public static Date getCountWeatherLastUpdated()
	{
		return getInstance().countWeathersWO.getLastUpdated();
	}

	/*
	 * @Deprecated choose the object you want.
	 */
	@Deprecated
	public static DelayVehicleTypeWO getDelayVehicleTypeWO()
	{
		return getInstance().delayVehicleTypeWO;
	}

	public static List<DelaySituationData> getDelaysSituation()
	{
		return getInstance().delaySituationWO.getData();
	}

	public static Date getDelaysSituationLastUpdated()
	{
		return getInstance().delaySituationWO.getLastUpdated();
	}

	public static DelayRequest getDelayRequestFor(StationID stationID)
	{
		return new DelayRequestDB(stationID);
	}

	public static DelayRequestFixedTime getDelayRequestForFixedTimeFor(StationID stationID)
	{
		return new DelayRequestFixedTimeDB(stationID);
	}

	public static List<Line> getLinesAt(StationID stationID)
	{
		try
		{
			return new LinesAtStationDB().getLinesAt(stationID);
		}
		catch (IOException e)
		{
			return new ArrayList<>();
		}
	}
}
