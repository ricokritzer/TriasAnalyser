package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.time.DelayHourData;

public class DelayHourDB implements DelayHourData
{
	private static final Logger LOGGER = Logger.getLogger(DelayHourDB.class.getName());

	private final double average;
	private final double maximum;
	private final int value;

	public DelayHourDB(double delayAverage, double delayMaximum, int value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	private static final Optional<DelayHourData> getDelays(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final int value = result.getInt("hour");

			return Optional.of(new DelayHourDB(delayAverage, delayMaximum, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayHourDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayHourData> getDelays() throws IOException
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

	@Override
	public double getDelayMaximum()
	{
		return maximum;
	}

	@Override
	public double getDelayAverage()
	{
		return average;
	}

	@Override
	public int getHour()
	{
		return value;
	}
}
