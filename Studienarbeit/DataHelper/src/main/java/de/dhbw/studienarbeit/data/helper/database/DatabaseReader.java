package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
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

	public DatabaseReader() throws IOException
	{
		super();
	}

	@Override
	protected void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseReaderUser(),
				Settings.getInstance().getDatabaseReaderPassword());
	}

	public List<ApiKey> readApiKeys(final String name) throws SQLException
	{
		ResultSet result = null;

		final String sql = new StringBuilder() //
				.append("SELECT * FROM Api WHERE name = '") //
				.append(name) //
				.append("';") //
				.toString();

		try (PreparedStatement stmt = connection.prepareStatement(sql))
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
				final String url = result.getString("url");

				apiKeys.add(new ApiKey(key, requests, url));
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
				final String name = result.getString("name");
				final double lat = result.getDouble("lat");
				final double lon = result.getDouble("lon");
				final String operator = result.getString("operator");

				stationDB.add(new StationDB(stationID, name, lat, lon, operator));
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
