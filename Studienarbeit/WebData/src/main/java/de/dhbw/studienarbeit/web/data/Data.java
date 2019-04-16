package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.DelayLineData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestFixedTime;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestFixedTimeDB;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestTimespan;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestTimespanDB;
import de.dhbw.studienarbeit.data.reader.data.request.LineDestinationsAtStationDB;
import de.dhbw.studienarbeit.data.reader.data.request.LineNamesAtStationDB;
import de.dhbw.studienarbeit.data.reader.data.request.LinesAtStationDB;
import de.dhbw.studienarbeit.data.reader.data.request.Request;
import de.dhbw.studienarbeit.data.reader.data.request.RequestDB;
import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituationData;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbourData;
import de.dhbw.studienarbeit.data.reader.data.station.StationData;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.DelayTimeSpanData;
import de.dhbw.studienarbeit.data.reader.data.time.DelayWeekdayData;
import de.dhbw.studienarbeit.data.reader.data.vehicletype.VehicleType;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.Clouds;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.Humidity;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.Pressure;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.DelayWeatherSymbolData;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.Temperature;
import de.dhbw.studienarbeit.data.reader.data.weather.text.WeatherText;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.Wind;
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
import de.dhbw.studienarbeit.web.data.weather.clouds.CloudsWO;
import de.dhbw.studienarbeit.web.data.weather.humidity.HumidityWO;
import de.dhbw.studienarbeit.web.data.weather.pressure.PressureWO;
import de.dhbw.studienarbeit.web.data.weather.symbol.DelayWeatherSymbolWO;
import de.dhbw.studienarbeit.web.data.weather.temperature.TemperatureWO;
import de.dhbw.studienarbeit.web.data.weather.text.DelayWeatherTextWO;
import de.dhbw.studienarbeit.web.data.weather.wind.WindWO;

public class Data
{
	private static final Logger LOGGER = Logger.getLogger(Data.class.getName());

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

	private final CloudsWO cloudsWO;
	private final HumidityWO humidityWO;
	private final PressureWO pressureWO;
	private final TemperatureWO temperatureWO;
	private final WindWO windWO;

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

		cloudsWO = new CloudsWO(updater);
		humidityWO = new HumidityWO(updater);
		pressureWO = new PressureWO(updater);
		temperatureWO = new TemperatureWO(updater);
		windWO = new WindWO(updater);

		delayWeatherTextWO = new DelayWeatherTextWO(updater);
		delayWeatherSymbolWO = new DelayWeatherSymbolWO(updater);

		stationNeighbourWO = new DelayStationNeighbourWO(updater);

		delaySituationWO = new DelaySituationWO(updater);
	}

	public static Data getInstance()
	{
		return INSTANCE;
	}

	public static final List<DelayStationData> getDelaysStation()
	{
		return getInstance().delaysStationWO.getData();
	}

	public static final Date getDelaysStationLastUpdate()
	{
		return getInstance().delaysStationWO.getLastUpdated();
	}

	public static final List<DelayLineData> getDelaysLine()
	{
		return getInstance().delayLineWO.getData();
	}

	public static final Date getDelaysLineLastUpdated()
	{
		return getInstance().delayLineWO.getLastUpdated();
	}

	public static final List<DelayData<VehicleType>> getDelaysVehicleType()
	{
		return getInstance().delayVehicleTypeWO.getData();
	}

	public static final Date getDelaysVehicleTypeLastUpdate()
	{
		return getInstance().delayVehicleTypeWO.getLastUpdated();
	}

	public static final List<DelayData<Temperature>> getDelaysTemperature()
	{
		return getInstance().temperatureWO.getDelays();
	}

	public static final DelayCorrelationData<Temperature> getDelayTemperatureCorrelationCoefficient()
	{
		return getInstance().temperatureWO.getCorrelation();
	}

	public static final List<CancelledStopsData<Temperature>> getCancelledStopsTemperature()
	{
		return getInstance().temperatureWO.getCancelledStops();
	}

	public static final Date getTemperatureLastUpdated()
	{
		return getInstance().temperatureWO.getLastUpdated();
	}

	public static final List<DelayData<Clouds>> getDelaysClouds()
	{
		return getInstance().cloudsWO.getDelays();
	}

	public static final DelayCorrelationData<Clouds> getDelayCloudsCorrelationCoefficient()
	{
		return getInstance().cloudsWO.getCorrelation();
	}

	public static final List<CancelledStopsData<Clouds>> getCancelledStopsClouds()
	{
		return getInstance().cloudsWO.getCancelledStops();
	}

	public static final Date getCloudsLastUpdated()
	{
		return getInstance().cloudsWO.getLastUpdated();
	}

	public static final List<DelayData<WeatherText>> getDelaysWeatherText()
	{
		return getInstance().delayWeatherTextWO.getData();
	}

	public static final Date getDelaysWeatherTextLastUpdated()
	{
		return getInstance().delayWeatherTextWO.getLastUpdated();
	}

	public static final List<DelayStationNeighbourData> getStationNeighbours()
	{
		return getInstance().stationNeighbourWO.getData();
	}

	public static final Date getStationNeighboursLastUpdated()
	{
		return getInstance().stationNeighbourWO.getLastUpdated();
	}

	public static final List<DelayData<Wind>> getDelaysWind()
	{
		return getInstance().windWO.getDelays();
	}

	public static final DelayCorrelationData<Wind> getDelayWindCorrelationCoefficient()
	{
		return getInstance().windWO.getCorrelation();
	}

	public static final List<CancelledStopsData<Wind>> getCancelledStopsWind()
	{
		return getInstance().windWO.getCancelledStops();
	}

	public static final Date getWindLastUpdated()
	{
		return getInstance().windWO.getLastUpdated();
	}

	public static final List<DelayData<Humidity>> getDelaysHumidity()
	{
		return getInstance().humidityWO.getDelays();
	}

	public static final DelayCorrelationData<Humidity> getDelayHumidityCorrelationCoefficient()
	{
		return getInstance().humidityWO.getCorrelation();
	}

	public static final List<CancelledStopsData<Humidity>> getCancelledStopsHumidity()
	{
		return getInstance().humidityWO.getCancelledStops();
	}

	public static final Date getHumidityLastUpdated()
	{
		return getInstance().humidityWO.getLastUpdated();
	}

	public static final List<DelayData<Pressure>> getDelaysPressure()
	{
		return getInstance().pressureWO.getDelays();
	}

	public static final DelayCorrelationData<Pressure> getDelayPressureCorrelationCoefficient()
	{
		return getInstance().pressureWO.getCorrelation();
	}

	public static final List<CancelledStopsData<Pressure>> getCancelledStopsPressure()
	{
		return getInstance().pressureWO.getCancelledStops();
	}

	public static final Date getPressureLastUpdated()
	{
		return getInstance().pressureWO.getLastUpdated();
	}

	public static final List<DelayWeekdayData> getDelaysWeekday()
	{
		return getInstance().delayWeekdayWO.getData();
	}

	public static final Date getDelaysWeekdayLastUpdated()
	{
		return getInstance().delayWeekdayWO.getLastUpdated();
	}

	public static final List<DelayTimeSpanData> getDelaysTimeSpan()
	{
		return getInstance().delayTimeSpanWO.getData();
	}

	public static final Date getDelaysTimeSpanLastUpdated()
	{
		return getInstance().delayTimeSpanWO.getLastUpdated();
	}

	public static final List<DelayWeatherSymbolData> getDelaysWeatherSymbols()
	{
		return getInstance().delayWeatherSymbolWO.getData();
	}

	public static final Date getDelaysWeatherSymbolsLastUpdated()
	{
		return getInstance().delayWeatherSymbolWO.getLastUpdated();
	}

	@Deprecated
	public static final DelayWeatherSymbolWO getDelaysWeatherSymbol()
	{
		return getInstance().delayWeatherSymbolWO;
	}

	@Deprecated
	public static final DelayWeatherSymbolWO getDelaysWeatherSymbolLastUpdated()
	{
		return getInstance().delayWeatherSymbolWO;
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

	public static List<DelaySituationData> getDelaysSituation()
	{
		return getInstance().delaySituationWO.getData();
	}

	public static Date getDelaysSituationLastUpdated()
	{
		return getInstance().delaySituationWO.getLastUpdated();
	}

	@Deprecated
	public static DelayRequestTimespan getDelayRequestFor(StationID stationID)
	{
		return new DelayRequestTimespanDB(stationID);
	}

	@Deprecated
	public static DelayRequestFixedTime getDelayRequestForFixedTimeFor(StationID stationID)
	{
		return new DelayRequestFixedTimeDB(stationID);
	}

	@Deprecated
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

	@Deprecated
	public static List<LineName> getLineNamesFor(StationID stationID, List<LineDestination> lineDestinations)
	{
		try
		{
			return new LineNamesAtStationDB().getLineNamesAt(stationID, lineDestinations);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Selecting does not succeed.", e);
			return new ArrayList<>();
		}
	}

	@Deprecated
	public static List<LineDestination> getLineDestinationFor(StationID stationID, List<LineName> lineNames)
	{
		try
		{
			return new LineDestinationsAtStationDB().getLineDestinationsAt(stationID, lineNames);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Selecting does not succeed.", e);
			return new ArrayList<>();
		}
	}

	@Deprecated
	public static Request createRequestFor(StationID stationID)
	{
		return new RequestDB(stationID);
	}

	public static Request createRequestFor(StationData station)
	{
		return new RequestDB(station);
	}
}
