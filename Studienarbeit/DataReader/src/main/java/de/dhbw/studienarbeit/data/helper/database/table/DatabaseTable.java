package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.SettingsReadOnly;
import de.dhbw.studienarbeit.data.helper.database.DatabaseConnector;
import de.dhbw.studienarbeit.data.helper.database.SqlCondition;

public abstract class DatabaseTable extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final String ENTRIES_READ = " entries read at table ";
	private static final String ALL = "*";
	private static final Logger LOGGER = Logger.getLogger(DatabaseTable.class.getName());

	protected String createSQLStatement(final String tableName, final SqlCondition... condition)
	{
		return createSQLStatement(ALL, tableName, condition);
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
		select(consumer, ALL, tableName, conditions);
	}

	private void select(Consumer<ResultSet> consumer, String what, String tableName, SqlCondition... conditions)
			throws SQLException
	{
		reconnectIfNeccessary();

		final String sql = createSQLStatement(what, tableName, conditions);
		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
			int counter = 0;
			while (result.next())
			{
				consumer.accept(result);
				counter++;
			}
			LOGGER.log(Level.FINE, counter + ENTRIES_READ + tableName);
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
		connectToDatabase(SettingsReadOnly.getInstance().getDatabaseHostname(),
				SettingsReadOnly.getInstance().getDatabasePort(), SettingsReadOnly.getInstance().getDatabaseName(),
				SettingsReadOnly.getInstance().getDatabaseReaderUser(),
				SettingsReadOnly.getInstance().getDatabaseReaderPassword());
	}

	private Optional<Integer> getTotal(ResultSet result)
	{
		try
		{
			return Optional.ofNullable(result.getInt("total"));
		}
		catch (SQLException e)
		{
			return Optional.empty();
		}
	}

	protected int count(final String tableName, final SqlCondition... conditions) throws SQLException
	{
		final String what = "COUNT(*) AS total";
		final List<Integer> count = new ArrayList<>();
		select(result -> getTotal(result).ifPresent(count::add), what, tableName, conditions);
		if (count.isEmpty())
		{
			throw new SQLException("Unable to count entries in " + tableName);
		}
		int c = count.get(0);
		LOGGER.log(Level.FINE, c + " entries at " + tableName);
		return count.get(0);
	}

	protected abstract String getTableName();

	public int count(SqlCondition... conditions) throws IOException
	{
		try
		{
			return count(getTableName(), conditions);
		}
		catch (SQLException e)
		{
			throw new IOException("Counting does not succeed.", e);
		}
	}
}
