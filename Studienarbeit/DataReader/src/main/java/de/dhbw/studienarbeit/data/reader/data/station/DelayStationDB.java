package de.dhbw.studienarbeit.data.reader.data.station;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.DelayDB;

public class DelayStationDB extends DelayDB<StationData>
{
	@Override
	protected StationData getElement(ResultSet result) throws SQLException
	{
		final StationID stationID = new StationID(result.getString("stationID"));
		final StationName name = new StationName(result.getString("name"));
		final OperatorName operator = new OperatorName(result.getString("displayName"));
		final Position position = new Position(result.getDouble("lat"), result.getDouble("lon"));

		return new StationData(stationID, name, position, operator);
	}

	@Override
	protected String getSQL()
	{
		return "SELECT Stop.stationID, name, displayName, lat, lon, count(*) AS count, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Station, Operator WHERE realTime IS NOT NULL AND Station.operator = Operator.operator AND Stop.stationID = Station.stationID GROUP BY Station.stationID ORDER BY delay_avg DESC;";
	}
}
