package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableApi;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataManager;

public class DataTriasApp
{
	List<Station> stations = new ArrayList<>();

	public void startDataCollection(String operator, List<StationDB> stationsDB) throws IOException
	{
		stations = stationsDB.parallelStream().map(stationDB -> new Station(stationDB.getStationID(),
				stationDB.getName(), stationDB.getLat(), stationDB.getLat(), stationDB.getOperator()))
				.collect(Collectors.toList());
		DataManager dm = new DataManager(new DatabaseSaver(), new DatabaseTableApi().selectApisByName(operator));
		dm.add(stations);
	}
}
