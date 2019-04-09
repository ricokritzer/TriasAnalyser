package de.dhbw.studienarbeit.data.reader.data.station;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayStationDB extends DB<DelayStationData> implements DelayStation
{
	public final List<DelayStationData> getDelays() throws IOException
	{
		final String sql = "SELECT Stop.stationID, name, displayName, lat, lon, count(*) AS count, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Station, Operator WHERE realTime IS NOT NULL AND Station.operator = Operator.operator AND Stop.stationID = Station.stationID GROUP BY Station.stationID ORDER BY delay_avg DESC;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayStationData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum maximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage average = new DelayAverage(result.getDouble("delay_avg"));
		final StationID stationID = new StationID(result.getString("stationID"));
		final StationName name = new StationName(result.getString("name"));
		final OperatorName operator = new OperatorName(result.getString("displayName"));
		final Position position = new Position(result.getDouble("lat"), result.getDouble("lon"));
		final CountData count = new CountData(result.getInt("count"));

		return Optional.of(new DelayStationData(maximum, average, stationID, name, operator, position, count));
	}
}
