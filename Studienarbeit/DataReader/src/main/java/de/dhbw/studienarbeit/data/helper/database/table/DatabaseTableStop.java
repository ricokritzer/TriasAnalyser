package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.model.DelayLineDB;

public class DatabaseTableStop extends DatabaseTable
{
	private static final String DELAY_MAX = "delay_max";
	private static final String DELAY_AVG = "delay_avg";
	private static final String DELAY_SUM = "delay_sum";
	private static final String TABLE_NAME = "Stop";

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
			select(r -> DelayDB.getDelay(r).ifPresent(list::add), preparedStatement);
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

		final String sql = DelayLineDB.getSQL();
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<DelayLineDB> list = new ArrayList<>();
			select(r -> DelayLineDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
