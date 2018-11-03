package de.dhbw.studienarbeit.data.helper.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.model.LineDB;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.model.StopDB;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseConverter
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseConverter.class.getName());

	private DatabaseConverter()
	{}

	public static Optional<StationDB> getStation(ResultSet result)
	{
		try
		{
			final String stationID = result.getString("stationID");
			final String name = result.getString("name");
			final double lat = result.getDouble("lat");
			final double lon = result.getDouble("lon");
			final String operator = result.getString("operator");
			return Optional.of(new StationDB(stationID, name, lat, lon, operator));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to station.", e);
			return Optional.empty();
		}
	}

	public static Optional<StopDB> getStop(ResultSet result)
	{
		try
		{
			final int stopID = result.getInt("stopID");
			final String stationID = result.getString("stationID");
			final int lineID = result.getInt("lineID");
			final Date timeTabledTime = result.getDate("timeTabledTime");
			final Date realTime = result.getDate("realTime");

			return Optional.of(new StopDB(stopID, stationID, lineID, timeTabledTime, realTime));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public static Optional<LineDB> getLine(ResultSet result)
	{
		try
		{
			final int lineID = result.getInt("lineID");
			final String lineName = result.getString("name");
			final String lineDestination = result.getString("destination");
			return Optional.of(new LineDB(lineID, lineName, lineDestination));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to line.", e);
			return Optional.empty();
		}
	}

	public static Optional<ApiKey> getApiKey(ResultSet result)
	{
		try
		{
			final String key = result.getString("apiKey");
			final int requests = result.getInt("maximumRequests");
			final String url = result.getString("url");
			return Optional.of(new ApiKey(key, requests, url));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to api key.", e);
			return Optional.empty();
		}
	}
}
