package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayDB;

public class DelayVehicleTypeDB extends DelayDB<VehicleType> implements Delay<VehicleType>
{
	@Override
	protected VehicleType getElement(ResultSet result) throws SQLException
	{
		return new VehicleType(result.getString("type"));
	}

	@Override
	protected String getSQL()
	{
		return "SELECT " + "SUBSTRING_INDEX(name, ' ', 1 ) AS type, " + "count(*) AS total, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY type ORDER BY delay_avg DESC;";
	}
}
