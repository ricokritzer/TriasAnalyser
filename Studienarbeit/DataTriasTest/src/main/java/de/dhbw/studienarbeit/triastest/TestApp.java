package de.dhbw.studienarbeit.triastest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKeyData;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.reader.data.api.ApiKeyDB;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.data.station.StationDB;
import de.dhbw.studienarbeit.data.trias.Station;

public class TestApp
{
	private static final Logger LOGGER = Logger.getLogger(TestApp.class.getName());
	private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	private static DataManager manager;
	private static final Timer timer = new Timer();

	public static void main(String[] args) throws ParseException
	{
		LogLevelHelper.setLogLevel(Level.FINEST);

		scheduleTest(1, "19.02.2019 14:04", "19.02.2019 14:10");
	}

	private static void scheduleTest(int maximumRequests, String start, String end) throws ParseException
	{
		scheduleTest(maximumRequests, FORMAT.parse(start), FORMAT.parse(end));
		LOGGER.log(Level.INFO, "Test scheduled: " + maximumRequests + " requests, start: " + start + " end: " + end);
	}

	private static void scheduleTest(int maximumRequests, Date start, Date end)
	{
		timer.schedule(new MyTimerTask(() -> setMaximumRequests(maximumRequests)), start);
		timer.schedule(new MyTimerTask(TestApp::stop), end);
	}

	private static void stop()
	{
		Optional.ofNullable(manager).ifPresent(DataManager::stop);
	}

	private static void setMaximumRequests(int number)
	{
		LOGGER.log(Level.INFO, "update to " + number);

		try
		{
			final OperatorID operator = new OperatorID("kvv");
			final ApiKeyData keyFromDB = ApiKeyDB.getApiKeys(operator).get(0);
			final ApiKeyData testApiKey = new ApiKeyData(keyFromDB.getKey(), number, keyFromDB.getUrl());
			manager = new DataManager("no name", new ArrayList<>());
			manager.addApiKey(testApiKey);

			final List<StationDB> stationsDB = StationDB.getObservedStations(operator);
			manager.add(stationsDB
					.parallelStream().map(stationDB -> new Station(stationDB.getStationID(), stationDB.getName(),
							stationDB.getLat(), stationDB.getLat(), stationDB.getOperator()))
					.collect(Collectors.toList()));
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to set new maximum.", e);
		}
	}
}
