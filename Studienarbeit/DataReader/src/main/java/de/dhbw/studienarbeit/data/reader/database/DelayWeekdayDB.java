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

import de.dhbw.studienarbeit.data.reader.data.time.DelayWeekdayData;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class DelayWeekdayDB implements DelayWeekdayData
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeekdayDB.class.getName());

	private final double average;
	private final double maximum;
	private final Weekday value;

	public DelayWeekdayDB(double delayAverage, double delayMaximum, Weekday value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	private static final Optional<DelayWeekdayData> getDelays(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final Weekday value = Weekday.values()[result.getInt("weekday")];

			return Optional.of(new DelayWeekdayDB(delayAverage, delayMaximum, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWeekdayDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayWeekdayData> getDelays() throws IOException
	{
		final String sql = "SELECT WEEKDAY(timetabledTime) AS weekday, avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_avg, max(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_max FROM Stop GROUP BY weekday;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWeekdayData> list = new ArrayList<>();
			database.select(r -> DelayWeekdayDB.getDelays(r).ifPresent(list::add), preparedStatement);
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
	public Weekday getWeekday()
	{
		return value;
	}
}
