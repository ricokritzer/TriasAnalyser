package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayVehicleTypeDB extends DB<DelayVehicleTypeData> implements DelayVehicleType
{
	public final List<DelayVehicleTypeData> getDelays() throws IOException
	{
		final String sql = "SELECT " + "SUBSTRING_INDEX(name, ' ', 1 ) AS type, " + "count(*) AS total, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY type ORDER BY delay_avg DESC;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayVehicleTypeData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final CountData count = new CountData(result.getInt("total"));
		final String type = result.getString("type");

		return Optional.of(new DelayVehicleTypeData(delayMaximum, delayAverage, count, type));
	}

	public static void main(String[] args) throws IOException
	{
		new DelayVehicleTypeDB().getDelays().forEach(e -> System.out.println(e));
	}
}
