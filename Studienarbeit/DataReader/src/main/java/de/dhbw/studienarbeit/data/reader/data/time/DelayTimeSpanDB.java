package de.dhbw.studienarbeit.data.reader.data.time;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayDB;

public class DelayTimeSpanDB extends DelayDB<TimeSpan> implements Delay<TimeSpan>
{
	@Override
	protected TimeSpan getElement(ResultSet result) throws SQLException
	{
		return TimeSpan.values()[result.getInt("hour")];
	}

	@Override
	protected String getSQL()
	{
		return "SELECT HOUR(timetabledTime) AS hour, count(*) AS total, avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_avg, max(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_max FROM Stop GROUP BY hour;";
	}
}
