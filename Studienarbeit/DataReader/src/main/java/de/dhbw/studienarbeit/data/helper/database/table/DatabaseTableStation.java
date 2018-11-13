package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.model.StationDB;

public class DatabaseTableStation extends DatabaseTable
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableStation.class.getName());
	private static final String TABLE_NAME = "Station";

	private final Optional<StationDB> getStation(ResultSet result)
	{
		try
		{
			final String stationID = result.getString("stationID");
			final String name = result.getString("name");
			final double lat = result.getDouble("lat");
			final double lon = result.getDouble("lon");
			final String operator = result.getString("operator");
			final boolean observe = result.getBoolean("observe");
			return Optional.of(new StationDB(stationID, name, lat, lon, operator, observe));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to station.", e);
			return Optional.empty();
		}
	}

	private final Optional<String> getOperator(ResultSet result)
	{
		try
		{
			final String operator = result.getString("operator");
			return Optional.of(operator);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to Operator.", e);
			return Optional.empty();
		}
	}

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}

	public final List<String> selectOperators() throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT DISTINCT operator FROM Station";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<String> observedStations = new ArrayList<>();
			select(r -> getOperator(r).ifPresent(observedStations::add), preparedStatement);
			return observedStations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public final List<String> selectObservedOperators() throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT DISTINCT operator FROM Station WHERE observe = true;";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<String> observedStations = new ArrayList<>();
			select(r -> getOperator(r).ifPresent(observedStations::add), preparedStatement);
			return observedStations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public final List<StationDB> selectObservedStations() throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE observe = true;";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<StationDB> observedStations = new ArrayList<>();
			select(r -> getStation(r).ifPresent(observedStations::add), preparedStatement);
			return observedStations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public final List<StationDB> selectObservedStations(final String operator) throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE observe = true AND operator = ?;";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setString(1, operator);

			final List<StationDB> observedStations = new ArrayList<>();
			select(r -> getStation(r).ifPresent(observedStations::add), preparedStatement);
			return observedStations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
