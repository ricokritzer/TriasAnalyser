package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.DatabaseConnector;

public class DatabaseTable extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final Logger LOGGER = Logger.getLogger(DatabaseTable.class.getName());

	public void select(Consumer<ResultSet> consumer, PreparedStatement statement) throws SQLException
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

	public Count count(String table) throws IOException
	{
		reconnectIfNeccessary();

		final String countEntries = "SELECT COUNT(*) AS total FROM " + table + ";";
		try (PreparedStatement preparedStatement = connection.prepareStatement(countEntries))
		{
			final List<Count> count = new ArrayList<>();
			select(result -> Count.getCount(result).ifPresent(count::add), preparedStatement);

			if (count.isEmpty())
			{
				throw new SQLException("Unable to count entries in " + table);
			}

			Count c = count.get(0);
			LOGGER.log(Level.FINEST, c + " entries at " + table);
			return count.get(0);
		}
		catch (SQLException e)
		{
			throw new IOException("Unable to count " + table, e);
		}
	}

	public PreparedStatement getPreparedStatement(String sql) throws IOException
	{
		reconnectIfNeccessary();
		try
		{
			return connection.prepareStatement(sql);
		}
		catch (SQLException e)
		{
			throw new IOException("Unable to prepare statement.", e);
		}
	}
}
