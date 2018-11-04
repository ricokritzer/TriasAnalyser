package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.DatabaseConnector;
import de.dhbw.studienarbeit.data.helper.database.SqlCondition;

public abstract class DatabaseTable extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final String START_READING_AT_TABLE = "Start reading at table ";
	private static final String ENTRIES_READ = " entries read.";
	private static final String SELECT_FROM = "*";
	private static final Logger LOGGER = Logger.getLogger(DatabaseTable.class.getName());

	protected String createSQLStatement(final String tableName, final SqlCondition... condition)
	{
		return createSQLStatement(SELECT_FROM, tableName, condition);
	}

	private String createSQLStatement(final String what, final String tableName, final SqlCondition... condition)
	{
		final StringBuilder sb = new StringBuilder("SELECT ").append(what).append(" FROM ").append(tableName);
		final List<String> conditionStrings = new ArrayList<>();
		Arrays.asList(condition).forEach(c -> conditionStrings.add(c.toString()));

		if (!conditionStrings.isEmpty())
		{
			sb.append(" WHERE ");
		}

		sb.append(String.join(" AND ", conditionStrings));
		sb.append(";");
		return sb.toString();
	}

	protected void select(Consumer<ResultSet> consumer, String tableName, SqlCondition... conditions)
			throws SQLException
	{
		LOGGER.log(Level.INFO, START_READING_AT_TABLE + tableName);

		reconnectIfNeccessary();

		final String sql = createSQLStatement(tableName, conditions);
		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
			int counter = 0;
			while (result.next())
			{
				consumer.accept(result);
				counter++;
			}
			LOGGER.log(Level.INFO, counter + ENTRIES_READ);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
		finally
		{
			disconnect();
		}
	}

	@Override
	protected void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseReaderUser(),
				Settings.getInstance().getDatabaseReaderPassword());
	}

	protected int count(final String tableName, final SqlCondition... conditions) throws SQLException
	{
		final String what = "COUNT(*) AS total";
		int count = 0;
		LOGGER.log(Level.INFO, START_READING_AT_TABLE + tableName);

		reconnectIfNeccessary();

		final String sql = createSQLStatement(what, tableName, conditions);
		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
			while (result.next())
			{
				count = result.getInt("total");
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
		finally
		{
			disconnect();
		}

		return count;
	}

	public abstract int count(SqlCondition... conditions) throws IOException;
}
