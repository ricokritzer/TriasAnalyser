package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class StopSituation implements Saveable
{
	private Situation situation;
	private Stop stop;

	public StopSituation(Situation situation, Stop stop)
	{
		super();
		this.situation = situation;
		this.stop = stop;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO StopSituation (situationID, version, stopID) "
				+ "SELECT * FROM (SELECT ? id, ? v, (SELECT stopID FROM Stop WHERE stationID = ? AND lineID = (SELECT lineID FROM Line WHERE name = ? AND destination = ?) AND timeTabledTime = ? AND realTime = ?) s) AS tmp "
				+ "WHERE NOT EXISTS (SELECT * FROM StopSituation WHERE situationID = id AND version = v AND "
				+ "stopID = s);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, situation.getId());
		preparedStatement.setInt(2, situation.getVersion());
		preparedStatement.setString(3, stop.getStationID());
		preparedStatement.setString(4, stop.getLineName());
		preparedStatement.setString(5, stop.getDestination());
		preparedStatement.setString(6, convertToStringWithoutMillis(stop.getTimeTabledTime()));

		final Optional<Date> realTime = stop.getRealTime();
		if (realTime.isPresent())
		{
			preparedStatement.setString(7, convertToStringWithoutMillis(realTime.get()));
		}
		else
		{
			preparedStatement.setTimestamp(7, null);
		}
	}

	private String convertToStringWithoutMillis(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

	}
}
