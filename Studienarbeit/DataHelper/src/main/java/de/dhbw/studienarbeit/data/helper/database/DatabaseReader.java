package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseReader extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final String START_READING_AT_TABLE = "Start reading at table ";
	private static final String LINES_READ = " lines read.";
	private static final String SELECT_FROM = "SELECT * FROM ";
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
		final String tableName = "Api";
		final String sql = createSQLStatement(tableName, new SqlCondition("name", name));
		LOGGER.log(Level.INFO, START_READING_AT_TABLE + tableName);

		reconnectIfNeccessary();

		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
			final List<ApiKey> apiKeys = new ArrayList<>();
			while (result.next())
			{
				final String key = result.getString("apiKey");
				final int requests = result.getInt("maximumRequests");
				final String url = result.getString("url");

				apiKeys.add(new ApiKey(key, requests, url));
			}
			LOGGER.log(Level.INFO, apiKeys.size() + LINES_READ);

			return apiKeys;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
	}

	public List<LineDB> readLine(final String destination, final String name) throws SQLException
	{
		final String tableName = "Line";
		final String sql = createSQLStatement(tableName, //
				new SqlCondition("name", name), //
				new SqlCondition("destination", destination));
		LOGGER.log(Level.INFO, START_READING_AT_TABLE + tableName);

		reconnectIfNeccessary();

		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
			final List<LineDB> lineDB = new ArrayList<>();
			while (result.next())
			{
				final int lineID = result.getInt("lineID");
				final String lineName = result.getString("name");
				final String lineDestination = result.getString("destination");

				lineDB.add(new LineDB(lineID, lineName, lineDestination));
			}
			LOGGER.log(Level.INFO, lineDB.size() + LINES_READ);

			return lineDB;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
	}

	public List<StationDB> readStations() throws SQLException
	{
		final String tableName = "Station";
		final String sql = createSQLStatement(tableName);
		LOGGER.log(Level.INFO, START_READING_AT_TABLE + tableName);

		reconnectIfNeccessary();

		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
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
			LOGGER.log(Level.INFO, stationDB.size() + LINES_READ);

			return stationDB;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
	}

	protected String createSQLStatement(final String tableName, final SqlCondition... condition)
	{
		final StringBuilder sb = new StringBuilder(SELECT_FROM).append(tableName);
		final List<String> conditionStrings = new ArrayList<>();
		Arrays.asList(condition).forEach(c -> conditionStrings.add(c.toString()));

		if (!conditionStrings.isEmpty())
		{
			sb.append(" WHERE ");
		}

		sb.append(String.join(" AND ", conditionStrings));
		sb.append(";");
		return sb.toString();
	}

	private void reconnectIfNeccessary() throws SQLException
	{
		if (connection.isClosed())
		{
			connectToDatabase();
		}
	}
}
