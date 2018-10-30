package de.dhbw.studienarbeit.data.helper.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseReader extends DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(TextSaver.class.getName());

	public DatabaseReader() throws SQLException, ReflectiveOperationException
	{
		super();
		connectToDatabase();
	}

	private void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseReaderUser(),
				Settings.getInstance().getDatabaseReaderPassword());
	}

	public List<ApiKey> readApiKeys(final String name) throws SQLException
	{
		ResultSet result = null;

		try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Api WHERE name = '" + name + "';"))
		{
			if (connection.isClosed())
			{
				connectToDatabase();
			}

			LOGGER.log(Level.INFO, "Start reading API keys.");
			result = stmt.executeQuery();
			final List<ApiKey> apiKeys = new ArrayList<>();
			while (result.next())
			{
				final String key = result.getString("apiKey");
				final int requests = result.getInt("maximumRequests");

				apiKeys.add(new ApiKey(key, requests));
			}
			LOGGER.log(Level.INFO, "Read " + apiKeys.size() + " API keys.");

			return apiKeys;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to read stations.", e);
			throw e;
		}
		finally
		{
			if (result != null)
			{
				result.close();
			}
		}
	}

	public List<StationDB> readStations() throws SQLException
	{
		ResultSet result = null;

		try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Station"))
		{
			if (connection.isClosed())
			{
				connectToDatabase();
			}

			LOGGER.log(Level.INFO, "Start reading stations.");
			result = stmt.executeQuery();
			final List<StationDB> stationDB = new ArrayList<>();
			while (result.next())
			{
				final String stationID = result.getString("stationID");
				final double lat = result.getDouble("lat");
				final double lon = result.getDouble("lon");

				stationDB.add(new StationDB(stationID, lat, lon));
			}
			LOGGER.log(Level.INFO, "Read " + stationDB.size() + " stations.");

			return stationDB;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to read stations.", e);
			throw e;
		}
		finally
		{
			if (result != null)
			{
				result.close();
			}
		}
	}
}
