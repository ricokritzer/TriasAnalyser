package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseReader extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final String START_READING_AT_TABLE = "Start reading at table ";
	private static final String ENTRYS_READ = " entrys read.";
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

	@Deprecated
	public List<ApiKey> readApiKeys(final String name) throws SQLException
	{
		return readApiKeys(new SqlCondition("name", name));
	}

	public List<ApiKey> readApiKeys(final SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Api";
		final String sql = createSQLStatement(tableName, conditions);
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
			LOGGER.log(Level.INFO, apiKeys.size() + ENTRYS_READ);

			return apiKeys;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
	}

	@Deprecated
	public List<LineDB> readLine(final String destination, final String name) throws SQLException
	{
		return readLine(new SqlCondition("name", name), //
				new SqlCondition("destination", destination));
	}

	public List<LineDB> readLine(final SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Line";
		final String sql = createSQLStatement(tableName, conditions);
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
			LOGGER.log(Level.INFO, lineDB.size() + ENTRYS_READ);

			return lineDB;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
	}

	@Deprecated
	public List<StationDB> readStations() throws SQLException
	{
		return readStations(new SqlCondition("observe", true));
	}

	public List<StationDB> readStations(final SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Station";
		final String sql = createSQLStatement(tableName, conditions);
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
			LOGGER.log(Level.INFO, stationDB.size() + ENTRYS_READ);

			return stationDB;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
	}

	public List<StopDB> readStops(SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Stop";
		final String sql = createSQLStatement(tableName, conditions);
		LOGGER.log(Level.INFO, START_READING_AT_TABLE + tableName);

		reconnectIfNeccessary();

		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
			final List<StopDB> stopDB = new ArrayList<>();
			while (result.next())
			{
				final int stopID = result.getInt("stopID");
				final String stationID = result.getString("stationID");
				final int lineID = result.getInt("lineID");
				final Date timeTabledTime = result.getDate("timeTabledTime");
				final Date realTime = result.getDate("realTime");

				stopDB.add(new StopDB(stopID, stationID, lineID, timeTabledTime, realTime));
			}
			LOGGER.log(Level.INFO, stopDB.size() + ENTRYS_READ);

			return stopDB;
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
