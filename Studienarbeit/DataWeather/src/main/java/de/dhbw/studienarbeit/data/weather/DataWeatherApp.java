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
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.helper.datamanagement.WaitingQueueCount;
import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.reader.database.ApiKeyDB;
import de.dhbw.studienarbeit.data.reader.database.Operator;
import de.dhbw.studienarbeit.data.reader.database.StationDB;

public class DataWeatherApp
{
	DataManager manager;
	
	public static void main(String[] args) throws IOException
	{
		LogLevelHelper.setLogLevel(Level.ALL);

		List<StationDB> testStations = new ArrayList<>();
		testStations.add(new StationDB("de:test:Karlsruhe", "Karlsruhe", 49.01, 8.40, "Stadt", true));
		testStations.add(new StationDB("de:test:Berlin", "Berlin", 52.521918, 13.413215, "Stadt", true));
		new DataWeatherApp().startDataCollection(testStations);
	}

	public void startDataCollection(final List<StationDB> stations) throws IOException
	{
		final Operator weatherOperator = new Operator("weather");
		final List<ApiKey> apiKeys = ApiKeyDB.getApiKeys(weatherOperator);
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

	protected List<Weather> convertToWeather(List<StationDB> stations)
	{
		final Set<Weather> weather = new HashSet<>();
		stations.forEach(s -> weather.add(convertToWeather(s)));
		return new ArrayList<>(weather);
	}

	private Weather convertToWeather(final StationDB station)
	{
		return new Weather(station.getLat(), station.getLon());
	}

	public void stopDataCollection()
	{
		manager.stop();
	}
}
