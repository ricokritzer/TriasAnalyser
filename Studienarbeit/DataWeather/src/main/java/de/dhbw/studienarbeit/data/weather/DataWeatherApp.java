package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKeyData;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.helper.datamanagement.WaitingQueueCount;
import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.reader.data.api.ApiKeyDB;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.data.station.ObservedStationData;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;

public class DataWeatherApp
{
	DataManager manager;

	public static void main(String[] args) throws IOException
	{
		LogLevelHelper.setLogLevel(Level.ALL);

		List<ObservedStationData> testStations = new ArrayList<>();
		testStations.add(new ObservedStationData(new StationID("de:test:Karlsruhe"), new StationName("Karlsruhe"),
				new Position(49.01, 8.40), new OperatorID("Stadt")));
		new DataWeatherApp().startDataCollection(testStations);
	}

	public void startDataCollection(final List<ObservedStationData> stations) throws IOException
	{
		final OperatorID weatherOperator = new OperatorID("weather");
		final List<ApiKeyData> apiKeys = new ApiKeyDB().getApiKeys(weatherOperator);
		final Date start = new Date();
		manager = new DataManager("weather", apiKeys);
		final List<Weather> weather = convertToWeather(stations);
		manager.add(weather);

		final Timer monitorTimer = new Timer();
		monitorTimer.scheduleAtFixedRate(new MyTimerTask(() -> saveWaitingQueueCount(manager.getWaitingQueueCount())),
				start, 60000l);
	}

	private void saveWaitingQueueCount(WaitingQueueCount waitingQueueCount)
	{
		DatabaseSaver.saveData(waitingQueueCount);
	}

	protected List<Weather> convertToWeather(List<ObservedStationData> stations)
	{
		final Set<Weather> weather = new HashSet<>();
		stations.forEach(s -> weather.add(convertToWeather(s)));
		return new ArrayList<>(weather);
	}

	private Weather convertToWeather(final ObservedStationData station)
	{
		return new Weather(station.getPosition().getLat(), station.getPosition().getLon());
	}

	public void stopDataCollection()
	{
		manager.stop();
	}
}
