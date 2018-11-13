package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
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
import de.dhbw.studienarbeit.data.helper.database.conditions.Condition;

public abstract class DatabaseTable extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final String ALL = "*";
	private static final Logger LOGGER = Logger.getLogger(DatabaseTable.class.getName());

	/*
	 * @deprecated use {@link #select(Consumer<ResultSet> consumer,
	 * PreparedStatement statement)} instead.
	 */
	@Deprecated
	protected String createSQLStatement(final String tableName, final Condition... condition)
	{
		return createSQLStatement(ALL, tableName, condition);
	}

	/*
	 * @deprecated use {@link #select(Consumer<ResultSet> consumer,
	 * PreparedStatement statement)} instead.
	 */
	@Deprecated
	private String createSQLStatement(final String what, final String tableName, final Condition... condition)
	{
		final StringBuilder sb = new StringBuilder("SELECT ").append(what).append(" FROM ").append(tableName);
		final List<String> conditionStrings = new ArrayList<>();
		Arrays.asList(condition).forEach(c -> conditionStrings.add(c.getSqlStatement()));

		if (!conditionStrings.isEmpty())
		{
			sb.append(" WHERE ");
		}

		sb.append(String.join(" AND ", conditionStrings));
		sb.append(";");
		return sb.toString();
	}

	/*
	 * @deprecated use {@link #select(Consumer<ResultSet> consumer,
	 * PreparedStatement statement)} instead.
	 */
	@Deprecated
	protected void select(Consumer<ResultSet> consumer, String tableName, Condition... conditions) throws SQLException
	{
		select(consumer, ALL, tableName, conditions);
	}

	protected void select(Consumer<ResultSet> consumer, PreparedStatement statement) throws SQLException
	{
		try (ResultSet result = statement.executeQuery())
		{
			while (result.next())
			{
				consumer.accept(result);
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ, e);
			throw e;
		}
		finally
		{
			disconnect();
		}
	}

	/*
	 * @deprecated use {@link #select(Consumer<ResultSet> consumer,
	 * PreparedStatement statement)} instead.
	 */
	@Deprecated
	protected void select(Consumer<ResultSet> consumer, String what, String tableName, Condition... conditions)
			throws SQLException
	{
		final String sql = createSQLStatement(what, tableName, conditions);
		select(consumer, connection.prepareStatement(sql));
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

	/*
	 * @deprecated use {@link #select(Consumer<ResultSet> consumer,
	 * PreparedStatement statement)} instead.
	 */
	@Deprecated
	protected int count(final String tableName, final Condition... conditions) throws SQLException
	{
		final String what = "COUNT(*) AS total";
		final List<Integer> count = new ArrayList<>();
		final String sql = createSQLStatement(tableName, what, conditions);
		select(result -> getTotal(result).ifPresent(count::add), sql);
		if (count.isEmpty())
		{
			throw new SQLException("Unable to count entries in " + tableName);
		}
		int c = count.get(0);
		LOGGER.log(Level.FINE, c + " entries at " + tableName);
		return count.get(0);
	}

	protected abstract String getTableName();

	public int count() throws IOException
	{
		final String countEntries = "SELECT COUNT(*) AS total FROM " + getTableName() + ";";

		PreparedStatement preparedStatement = null;
		try
		{
			reconnectIfNeccessary();

			preparedStatement = connection.prepareStatement(countEntries);

			final List<Integer> count = new ArrayList<>();
			select(result -> getTotal(result).ifPresent(count::add), preparedStatement);
			if (count.isEmpty())
			{
				throw new SQLException("Unable to count entries in " + getTableName());
			}
			int c = count.get(0);
			LOGGER.log(Level.FINE, c + " entries at " + getTableName());
			return count.get(0);
		}
		catch (SQLException e)
		{
			throw new IOException("Unable to count " + getTableName(), e);
		}
		finally
		{
			Optional.ofNullable(preparedStatement).ifPresent(stmt -> {
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
					// ignore
				}
			});
		}
	}

	/*
	 * @deprecated use {@link #count()} instead.
	 */
	public int count(Condition... conditions) throws IOException
	{
		final String what = "COUNT(*) AS total";
		final StringBuilder sb = new StringBuilder("SELECT ").append(what).append(" FROM ").append(getTableName());

		final List<String> conditionStrings = new ArrayList<>();
		Arrays.asList(conditions).forEach(c -> conditionStrings.add(c.getSqlStatement()));

		if (!conditionStrings.isEmpty())
		{
			sb.append(" WHERE ");
		}

		sb.append(String.join(" AND ", conditionStrings));
		sb.append(";");

		try
		{
			return count(getTableName(), conditions);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "SQLException occured.", e);
			throw new IOException("Counting does not succeed.", e);
		}
	}
}
