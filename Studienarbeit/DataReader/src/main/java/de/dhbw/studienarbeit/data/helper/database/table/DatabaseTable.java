package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.SettingsReadOnly;
import de.dhbw.studienarbeit.data.helper.database.DatabaseConnector;

public abstract class DatabaseTable extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final String ALL = "*";
	private static final Logger LOGGER = Logger.getLogger(DatabaseTable.class.getName());

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
}
