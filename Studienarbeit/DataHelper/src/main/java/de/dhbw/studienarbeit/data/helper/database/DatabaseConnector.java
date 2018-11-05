package de.dhbw.studienarbeit.data.helper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.saver.Saver;
import de.dhbw.studienarbeit.data.helper.database.saver.TextSaver;

public abstract class DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseSaver.class.getName());
	protected Saver saver = new TextSaver("errors.txt");

	protected Connection connection;

	protected abstract void connectToDatabase() throws SQLException;

	public void setSaverForErrors(Saver saver)
	{
		this.saver = saver;
	}

	protected void reconnectIfNeccessary() throws SQLException
	{
		if (!Optional.ofNullable(connection).isPresent() || connection.isClosed())
		{
			connectToDatabase();
		}
	}

	protected void connectToDatabase(final String username, final String password) throws SQLException
	{
		LOGGER.log(Level.INFO, "Connecting to database.");
		String url = "jdbc:mysql://" + Settings.getInstance().getDatabaseHostname() + ":"
				+ Settings.getInstance().getDatabasePort() + "/" + Settings.getInstance().getDatabaseName()
				+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		connection = DriverManager.getConnection(url, username, password);
		LOGGER.log(Level.INFO, "Connected to database.");
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
