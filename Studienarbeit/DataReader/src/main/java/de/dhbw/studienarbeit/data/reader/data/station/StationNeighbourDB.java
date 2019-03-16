package de.dhbw.studienarbeit.data.reader.data.station;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DB;

public class StationNeighbourDB extends DB<StationNeighbourData> implements StationNeighbour
{
	public final List<StationNeighbourData> getStationNeighbours() throws IOException
	{
		final String sql = "SELECT DISTINCT "
				+ "stationID1, station1.lat AS lat1, station1.lon AS lon1, station1.name AS name1, station1.operator AS operator1, "
				+ "stationID2, station2.lat AS lat2, station2.lon AS lon2, station2.name AS name2, station2.operator AS operator2 "
				+ "FROM StationNeighbour, Station station1, Station station2 "
				+ "WHERE StationNeighbour.stationID1 = station1.stationID AND StationNeighbour.stationID2 = station2.stationID;";

		return readFromDatabase(sql);
	}

	@Override
	protected Optional<StationNeighbourData> getValue(ResultSet result) throws SQLException
	{
		final StationID station1 = new StationID(result.getString("stationID1"));
		final StationName stationName1 = new StationName(result.getString("name1"));
		final Position position1 = new Position(result.getDouble("lat1"), result.getDouble("lon1"));
		final OperatorName operator1 = new OperatorName("operator1");
		final StationData stationFrom = new StationData(station1, stationName1, position1, operator1);

		final StationID station2 = new StationID(result.getString("stationID2"));
		final StationName stationName2 = new StationName(result.getString("name2"));
		final Position position2 = new Position(result.getDouble("lat2"), result.getDouble("lon2"));
		final OperatorName operator2 = new OperatorName("operator2");
		final StationData stationTo = new StationData(station2, stationName2, position2, operator2);

		return Optional.of(new StationNeighbourData(stationFrom, stationTo));
	}
}
