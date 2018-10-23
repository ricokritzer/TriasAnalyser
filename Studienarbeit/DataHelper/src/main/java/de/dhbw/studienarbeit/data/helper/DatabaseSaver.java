package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSaver
{
	private static final Logger LOGGER = Logger.getLogger(TextSaver.class.getName());

	private Connection connection;

	public static void main(String[] args) throws SQLException, ReflectiveOperationException
	{
		DatabaseSaver server = new DatabaseSaver();
		server.safe(new DataModel()
		{

			@Override
			public void updateData(int attempts) throws IOException
			{}

			@Override
			public Date nextUpdate()
			{
				return null;
			}

			@Override
			public String getSQLQuerry()
			{
				return "INSERT INTO TEST VALUES (1, 'test1');";
			}
		});
		server.disconnect();
	}

	public DatabaseSaver() throws SQLException, ReflectiveOperationException
	{
		loadDatabaseDriver();
		connectToDatabase();
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

	public void safe(DataModel model) throws SQLException
	{
		try (PreparedStatement stmt = connection.prepareStatement(model.getSQLQuerry()))
		{
			final int result = stmt.executeUpdate();
			LOGGER.log(Level.INFO, "Connecting to database.");

			// TODO
		}
	}

	private void connectToDatabase() throws SQLException
	{
		try
		{
			LOGGER.log(Level.INFO, "Connecting to database.");
			String url = "jdbc:mysql://" + ConfigurationData.DATABASE_HOSTNAME + ":" + ConfigurationData.DATABASE_PORT
					+ "/" + ConfigurationData.DATABASE_NAME
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			connection = DriverManager.getConnection(url, ConfigurationData.DATABASE_USER,
					ConfigurationData.DATABASE_PASSWORD);
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
}