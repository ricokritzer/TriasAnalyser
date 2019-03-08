package de.dhbw.studienarbeit.data.reader.data.time;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayHourDB implements DelayHour
{
	private static final Logger LOGGER = Logger.getLogger(DelayHourDB.class.getName());

	private static final Optional<DelayHourData> getDelays(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final Hour value = Hour.values()[result.getInt("hour")];

			return Optional.of(new DelayHourData(delayAverage, delayMaximum, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayHourDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public final List<DelayHourData> getDelays() throws IOException
	{
		final String sql = "SELECT HOUR(timetabledTime) AS hour, avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_avg, max(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_max FROM Stop GROUP BY hour;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayHourData> list = new ArrayList<>();
			database.select(r -> DelayHourDB.getDelays(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
