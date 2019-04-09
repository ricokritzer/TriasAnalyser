package de.dhbw.studienarbeit.data.reader.data.time;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayTimeSpanDB extends DB<DelayTimeSpanData> implements DelayTimeSpan
{
	public final List<DelayTimeSpanData> getDelays() throws IOException
	{
		final String sql = "SELECT HOUR(timetabledTime) AS hour, count(*) AS total, avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_avg, max(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_max FROM Stop GROUP BY hour;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayTimeSpanData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final CountData count = new CountData(result.getInt("total"));
		final TimeSpan value = TimeSpan.values()[result.getInt("hour")];

		return Optional.of(new DelayTimeSpanData(delayMaximum, delayAverage, count, value));
	}
}
