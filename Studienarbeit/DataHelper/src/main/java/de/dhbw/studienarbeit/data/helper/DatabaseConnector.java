package de.dhbw.studienarbeit.data.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseSaver.class.getName());
	protected Saver saver = new TextSaver("errors.txt");

	protected Connection connection;

	public DatabaseConnector() throws ReflectiveOperationException
	{
		loadDatabaseDriver();
	}

	public void setSaverForErrors(Saver saver)
	{
		this.saver = saver;
	}

	protected void connectToDatabase(final String username, final String password) throws SQLException
	{
		try
		{
			LOGGER.log(Level.INFO, "Connecting to database.");
			String url = "jdbc:mysql://" + ConfigurationData.DATABASE_HOSTNAME + ":" + ConfigurationData.DATABASE_PORT
					+ "/" + ConfigurationData.DATABASE_NAME
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			connection = DriverManager.getConnection(url, username, password);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "SQLException occured.", e);
			throw e;
		}
	}

	private void loadDatabaseDriver() throws ReflectiveOperationException
	{
		try
		{
			LOGGER.log(Level.INFO, "Loading database driver.");
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (ReflectiveOperationException e)
		{
			LOGGER.log(Level.WARNING, "Unable to load driver.", e);
			throw e;
		}
	}

	public void disconnect() throws SQLException
	{
		try
		{
			LOGGER.log(Level.INFO, "Close database connection.");
			connection.close();
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "SQLException occured.", e);
			throw e;
		}
	}
}
