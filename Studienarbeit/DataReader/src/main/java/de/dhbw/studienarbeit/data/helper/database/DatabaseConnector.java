package de.dhbw.studienarbeit.data.helper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.SettingsReadOnly;

public abstract class DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseConnector.class.getName());

	protected Connection connection;

	protected abstract void connectToDatabase() throws SQLException;

	protected void reconnectIfNeccessary() throws SQLException
	{
		if (!Optional.ofNullable(connection).isPresent() || connection.isClosed())
		{
			connectToDatabase();
		}
	}

	protected void connectToDatabase(final String username, final String password) throws SQLException
	{
		LOGGER.log(Level.FINE, "Connecting to database.");
		String url = "jdbc:mysql://" + SettingsReadOnly.getInstance().getDatabaseHostname() + ":"
				+ SettingsReadOnly.getInstance().getDatabasePort() + "/"
				+ SettingsReadOnly.getInstance().getDatabaseName()
				+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		connection = DriverManager.getConnection(url, username, password);
		LOGGER.log(Level.FINE, "Connected to database.");
	}

	public void disconnect() throws SQLException
	{
		try
		{
			LOGGER.log(Level.FINE, "Close database connection.");
			connection.close();
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.FINE, "SQLException occured.", e);
			throw e;
		}
	}
}
