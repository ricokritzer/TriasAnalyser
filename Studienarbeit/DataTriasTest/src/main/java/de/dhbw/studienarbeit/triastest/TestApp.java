package de.dhbw.studienarbeit.triastest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import de.dhbw.studienarbeit.data.trias.Station;

public class TestApp
{
	private static final Logger LOGGER = Logger.getLogger(TestApp.class.getName());
	private static DataManager manager;

	public static void main(String[] args) throws ParseException, IOException
	{
		manager = new DataManager(new DatabaseTableApi().selectApisByName("kvv"));
		// kvv(); // Rücksprache halten
		test(); // zum Ausprobieren
	}

	private static void test()
	{
		setMaximumRequests(1);
	}

	private static void kvv() throws ParseException
	{
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		final Timer t = new Timer();

		t.schedule(new MyTimerTask(() -> setMaximumRequests(500)), format.parse("2018-11-08 09-00-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-08 09-25-00"));
		t.schedule(new MyTimerTask(() -> setMaximumRequests(1000)), format.parse("2018-11-08 09-30-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-08 09-55-00"));
		t.schedule(new MyTimerTask(() -> setMaximumRequests(1500)), format.parse("2018-11-08 10-00-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-08 10-25-00"));
		t.schedule(new MyTimerTask(() -> setMaximumRequests(2000)), format.parse("2018-11-08 10-30-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-08 10-55-00"));
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
