package de.dhbw.studienarbeit.data.reader.data.station;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class ObservedStationDB extends DB<ObservedStationData> implements ObservedStation
{
	@Override
	public List<ObservedStationData> getObservedStations() throws IOException
	{
		final String sql = "SELECT * FROM Station WHERE observe = true;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<ObservedStationData> getValue(ResultSet result) throws SQLException
	{
		final StationID stationID = new StationID(result.getString("stationID"));
		final double lat = result.getDouble("lat");
		final double lon = result.getDouble("lon");
		final OperatorID operator = new OperatorID(result.getString("operator"));

		return Optional.of(new ObservedStationData(stationID, new Position(lat, lon), operator));
	}
}
