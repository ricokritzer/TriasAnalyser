package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseConnector.class.getName());

	protected Connection connection;

	protected abstract void connectToDatabase() throws SQLException;

	protected void reconnectIfNeccessary() throws IOException
	{
		try
		{
			if (!Optional.ofNullable(connection).isPresent() || connection.isClosed())
			{
				connectToDatabase();
			}
		}
		catch (SQLException e)
		{
			throw new IOException("Unable to connect to database.", e);
		}
	}

	private void loadDatabaseDriver()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			LOGGER.log(Level.WARNING, "Unable to load database driver.", e);
		}
	}

	protected void connectToDatabase(final String hostname, final String port, final String databaseName,
			final String username, final String password) throws SQLException
	{
		LOGGER.log(Level.FINE, "Connecting to database.");
		loadDatabaseDriver();
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName
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
			throw new SQLException("Unable to disconnect.", e);
		}
	}
}
