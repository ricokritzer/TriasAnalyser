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

public class DelayWeekdayDB extends DB<DelayWeekdayData> implements DelayWeekday
{
	public final List<DelayWeekdayData> getDelays() throws IOException
	{
		final String sql = "SELECT WEEKDAY(timetabledTime) AS weekday, count(*) AS total, avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_avg, max(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_max FROM Stop GROUP BY weekday;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayWeekdayData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final CountData count = new CountData(result.getInt("total"));
		final Weekday value = Weekday.values()[result.getInt("weekday")];

		return Optional.of(new DelayWeekdayData(delayMaximum, delayAverage, count, value));
	}

	public static void main(String[] args) throws IOException
	{
		new DelayWeekdayDB().getDelays().forEach(e -> System.out.println(e));
	}
}