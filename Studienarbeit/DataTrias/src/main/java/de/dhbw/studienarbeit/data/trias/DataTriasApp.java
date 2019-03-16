package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.helper.datamanagement.WaitingQueueCount;
import de.dhbw.studienarbeit.data.reader.data.api.ApiKeyDB;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.data.station.ObservedStationData;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;

public class DataTriasApp
{
	List<Station> stations = new ArrayList<>();
	DataManager manager;

	public static void main(String[] args) throws IOException
	{
		List<ObservedStationData> testStations = new ArrayList<>();
		testStations.add(new ObservedStationData(new StationID("de:08212:89"), new StationName("bla"),
				new Position(49.01, 8.40), new OperatorID("kvv")));
		new DataTriasApp().startDataCollection(new OperatorID("kvv"), testStations);
	}

	public void startDataCollection(OperatorID operator, List<ObservedStationData> stationsDB) throws IOException
	{
		stations = stationsDB.parallelStream()
				.map(stationDB -> new Station(stationDB.getStationID().getValue(),
						stationDB.getStationName().getStationName(), stationDB.getPosition().getLat(),
						stationDB.getPosition().getLon(), stationDB.getOperatorID().getName()))
				.collect(Collectors.toList());
		final Date start = new Date();
		manager = new DataManager(operator.getName(), new ApiKeyDB().getApiKeys(operator));
		manager.add(stations);

		final Timer monitorTimer = new Timer();
		monitorTimer.scheduleAtFixedRate(new MyTimerTask(() -> saveWaitingQueueCount(manager.getWaitingQueueCount())),
				start, 60000l);
	}

	private void saveWaitingQueueCount(WaitingQueueCount waitingQueueCount)
	{
		DatabaseSaver.saveData(waitingQueueCount);
	}

	public void stopDataCollection()
	{
		manager.stop();
	}
}
