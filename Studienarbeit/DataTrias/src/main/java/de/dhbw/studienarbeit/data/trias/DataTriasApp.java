package de.dhbw.studienarbeit.data.trias;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.studienarbeit.data.helper.database.StationDB;

public class DataTriasApp
{
	List<Station> stations = new ArrayList<>();

	public void startDataCollection(List<StationDB> stationsDB) throws SQLException, ReflectiveOperationException
	{
		stations = stationsDB.parallelStream().map(stationDB -> new Station(stationDB.getStationID(),
				stationDB.getName(), stationDB.getLat(), stationDB.getLat(), stationDB.getOperator()))
				.collect(Collectors.toList());
	}
}
