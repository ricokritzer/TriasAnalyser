package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	private Optional<DelayDB> getDelay(ResultSet result)
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
}
