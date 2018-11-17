package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.model.DelayLineDB;
import de.dhbw.studienarbeit.data.helper.database.model.StopDB;

public class DatabaseTableStop extends DatabaseTable
{
	private static final String DELAY_MAX = "delay_max";
	private static final String DELAY_AVG = "delay_avg";
	private static final String DELAY_SUM = "delay_sum";
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableStop.class.getName());
	private static final String TABLE_NAME = "Stop";

	private static Optional<StopDB> getStop(ResultSet result)
	{
		try
		{
			final int stopID = result.getInt("stopID");
			final String stationID = result.getString("stationID");
			final int lineID = result.getInt("lineID");
			final Date timeTabledTime = result.getDate("timeTabledTime");
			final Date realTime = result.getDate("realTime");

			return Optional.of(new StopDB(stopID, stationID, lineID, timeTabledTime, realTime));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	private static Optional<DelayDB> getDelay(ResultSet result)
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

	private static Optional<DelayLineDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble(DELAY_MAX);
			final double delayAverage = result.getDouble(DELAY_AVG);
			final String name = result.getString("name");
			final String destination = result.getString("destination");

			return Optional.of(new DelayLineDB(delayAverage, delayMaximum, name, destination));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}

	public final List<DelayDB> selectDelay() throws IOException
	{
		reconnectIfNeccessary();

		final String delaySQL = "(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime))";
		final String as = " AS ";

		final String sql = new StringBuilder().append("SELECT ") //
				.append("sum").append(delaySQL).append(as).append(DELAY_SUM).append(", ") //
				.append("avg").append(delaySQL).append(as).append(DELAY_AVG).append(", ") //
				.append("max").append(delaySQL).append(as).append(DELAY_MAX) //
				.append(" FROM ").append(TABLE_NAME).append(" WHERE realTime IS NOT NULL;").toString();
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<DelayDB> list = new ArrayList<>();
			select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public final List<DelayLineDB> selectDelaysByLineName() throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT name, destination, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY Stop.lineID;";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<DelayLineDB> list = new ArrayList<>();
			select(r -> getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
