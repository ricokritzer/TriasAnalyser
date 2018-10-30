package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;

public abstract class DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseSaver.class.getName());
	protected Saver saver = new TextSaver("errors.txt");

	protected Connection connection;

	public DatabaseConnector() throws IOException
	{
		try
		{
			connectToDatabase();
			loadDatabaseDriver();
		}
		catch (ReflectiveOperationException e)
		{
			throw new IOException("Unable to load database driver.", e);
		}
		catch (SQLException e)
		{
			throw new IOException("Connecting to database does not succeed.", e);
		}
	}

	protected abstract void connectToDatabase() throws SQLException;

	public void setSaverForErrors(Saver saver)
	{
		this.saver = saver;
	}

	protected void connectToDatabase(final String username, final String password) throws SQLException
	{
		try
		{
			LOGGER.log(Level.INFO, "Connecting to database.");
			String url = "jdbc:mysql://" + Settings.getInstance().getDatabaseHostname() + ":"
					+ Settings.getInstance().getDatabasePort() + "/" + Settings.getInstance().getDatabaseName()
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
