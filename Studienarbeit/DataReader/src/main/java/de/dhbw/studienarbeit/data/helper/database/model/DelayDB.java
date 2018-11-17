package de.dhbw.studienarbeit.data.helper.database.model;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTable;

public class DelayDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayDB.class.getName());

	private static final String DELAY_MAX = "delay_max";
	private static final String DELAY_AVG = "delay_avg";
	private static final String DELAY_SUM = "delay_sum";

	private final double maximum;
	private final double average;
	private final double summary;

	public DelayDB(double delaySummary, double delayAverage, double delayMaximum)
	{
		this.summary = delaySummary;
		this.average = delayAverage;
		this.maximum = delayMaximum;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getAverage()
	{
		return average;
	}

	public double getSum()
	{
		return summary;
	}

	public static final Optional<DelayDB> getDelay(ResultSet result)
	{
		try
		{
			final double delaySummary = result.getDouble(DELAY_SUM);
			final double delayAverage = result.getDouble(DELAY_AVG);
			final double delayMaximum = result.getDouble(DELAY_MAX);

			return Optional.of(new DelayDB(delaySummary, delayAverage, delayMaximum));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayDB> getDelay() throws IOException
	{
		final String sql = "SELECT " //
				+ "sum(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_sum, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL;";
		final DatabaseTable database = new DatabaseTable();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayDB> list = new ArrayList<>();
			database.select(r -> DelayDB.getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
