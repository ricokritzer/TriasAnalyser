package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.model.StopDB;

public class DatabaseTableStop extends DatabaseTable
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableStop.class.getName());
	private static final String TABLE_NAME = "Stop";

	protected static Date lastUpdated = new Date();
	protected static DelayDB delay;

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
			final double delaySummary = result.getDouble("delay_sum");
			final double delayAverage = result.getDouble("delay_avg");
			final double delayMaximum = result.getDouble("delay_max");

			return Optional.of(new DelayDB(delaySummary, delayAverage, delayMaximum));
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

	public final List<StopDB> selectStops(SqlCondition... conditions) throws IOException
	{
		try
		{
			final List<StopDB> list = new ArrayList<>();
			select(r -> getStop(r).ifPresent(list::add), TABLE_NAME, conditions);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	@Deprecated
	public final List<DelayDB> selectDelay(SqlCondition... conditions) throws IOException
	{
		try
		{
			final List<DelayDB> list = new ArrayList<>();
			final String what = " sum(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_sum,"
					+ "        avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg,"
					+ "        max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max";
			select(r -> getDelay(r).ifPresent(list::add), what, TABLE_NAME, conditions);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public final DelayDB getDelay() throws IOException
	{
		updateDelay();
		return delay;
	}

	public final void updateDelay() throws IOException
	{
		Calendar nextUpdateNotBefore = Calendar.getInstance();
		nextUpdateNotBefore.setTime(lastUpdated);
		nextUpdateNotBefore.add(Calendar.MINUTE, 1);

		if (nextUpdateNotBefore.after(new Date()))
		{
			try
			{
				final String delaySQL = "UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)";
				final String what = new StringBuilder() //
						.append("sum(").append(delaySQL).append(") AS").append("delay_sum") //
						.append("avg(").append(delaySQL).append(") AS").append("delay_avg") //
						.append("max(").append(delaySQL).append(") AS").append("delay_max") //
						.toString();
				select(DatabaseTableStop::setDelayDB, what, TABLE_NAME);
			}
			catch (SQLException e)
			{
				throw new IOException("Selecting does not succeed.", e);
			}
		}
	}

	private static final void setDelayDB(final ResultSet result)
	{
		final Optional<DelayDB> delayDB = getDelay(result);
		if (delayDB.isPresent())
		{
			DatabaseTableStop.delay = delayDB.get();
		}
	}
}
