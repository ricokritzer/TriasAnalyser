package de.dhbw.studienarbeit.triastest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableApi;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

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
		setTrueAtStations(5);
	}

	private static void kvv() throws ParseException
	{
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		final Timer t = new Timer();

		t.schedule(new MyTimerTask(() -> setTrueAtStations(500)), format.parse("2018-11-05 15-00-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-05 15-05-00"));
		t.schedule(new MyTimerTask(() -> setTrueAtStations(1000)), format.parse("2018-11-05 15-10-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-05 15-15-00"));
		t.schedule(new MyTimerTask(() -> setTrueAtStations(1500)), format.parse("2018-11-05 15-20-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-05 14-25-00"));
		t.schedule(new MyTimerTask(() -> setTrueAtStations(2000)), format.parse("2018-11-05 14-30-00"));
		t.schedule(new MyTimerTask(TestApp::stop), format.parse("2018-11-05 14-35-00"));
	}

	private static void stop()
	{
		Optional.ofNullable(manager).ifPresent(DataManager::stop);
	}

	private static void setTrueAtStations(int number)
	{
		LOGGER.log(Level.INFO, "update to " + number);

		// TODO Patrick, hier muss dein Zeug hin

		// END
		//
		// try
		// {
		// // manager = new DataManager(new DatabaseTableApi().selectApisByName("kvv"));
		// final List<StationDB> stationsDB = new
		// DatabaseTableStation().selectObservedStations("kvv");
		// manager.add(stationsDB
		// .parallelStream().map(stationDB -> new Station(stationDB.getStationID(),
		// stationDB.getName(),
		// stationDB.getLat(), stationDB.getLat(), stationDB.getOperator()))
		// .collect(Collectors.toList()));
		// }
		// catch (IOException e)
		// {
		// LOGGER.log(Level.WARNING, "Unable to manage data.", e);
		// }

	}
}
