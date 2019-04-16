package de.dhbw.studienarbeit.data.reader.data.time;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.DelayDB;

public class DelayWeekdayDB extends DelayDB<Weekday>
{
	@Override
	protected Weekday getElement(ResultSet result) throws SQLException
	{
		return Weekday.values()[result.getInt("weekday")];
	}

	@Override
	protected String getSQL()
	{
		return "SELECT WEEKDAY(timetabledTime) AS weekday, count(*) AS total, avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_avg, max(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_max FROM Stop GROUP BY weekday;";
	}
}