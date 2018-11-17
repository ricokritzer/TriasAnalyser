package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

import de.dhbw.studienarbeit.data.helper.database.model.ApiKeyDB;
import de.dhbw.studienarbeit.data.helper.database.model.Operator;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.helper.datamanagement.WaitingQueueCount;

public class DataTriasApp
{
	List<Station> stations = new ArrayList<>();

	public static void main(String[] args) throws IOException
	{
		List<StationDB> testStations = new ArrayList<>();
		testStations.add(new StationDB("de:08212:1", "Schulbustest", 49.01, 8.40, "kvv", true));
		new DataTriasApp().startDataCollection(new Operator("kvv"), testStations);
	}

	public void startDataCollection(Operator operator, List<StationDB> stationsDB) throws IOException
	{
		stations = stationsDB.parallelStream().map(stationDB -> new Station(stationDB.getStationID(),
				stationDB.getName(), stationDB.getLat(), stationDB.getLat(), stationDB.getOperator()))
				.collect(Collectors.toList());
		final Date start = new Date();
		final DataManager manager = new DataManager(operator.getName(), ApiKeyDB.getApiKeys(operator));
		manager.add(stations);

		final Timer monitorTimer = new Timer();
		monitorTimer.scheduleAtFixedRate(new MyTimerTask(() -> saveWaitingQueueCount(manager.getWaitingQueueCount())),
				start, 60000l);
	}

	private void saveWaitingQueueCount(WaitingQueueCount waitingQueueCount)
	{
		try
		{
			new DatabaseSaver().save(waitingQueueCount);
		}
		catch (IOException e)
		{
			// ignore
		}
	}
}
