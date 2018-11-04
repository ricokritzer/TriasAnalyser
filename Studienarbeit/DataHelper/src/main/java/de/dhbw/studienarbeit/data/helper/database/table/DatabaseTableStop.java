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
			throw new IOException("Selecting stations does not succeed.", e);
		}
	}
}
