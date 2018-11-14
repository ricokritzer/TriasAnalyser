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

import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableApi;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
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

		scheduleTest(400, "14.11.2018 14:25", "14.11.2018 14:40");
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
			final ApiKey keyFromDB = new DatabaseTableApi().selectApisByName("kvv").get(0);
			final ApiKey testApiKey = new ApiKey(keyFromDB.getKey(), number, keyFromDB.getUrl());
			manager = new DataManager(new ArrayList<>());
			manager.addApiKey(testApiKey);

			final List<StationDB> stationsDB = new DatabaseTableStation().selectObservedStations("kvv");
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
