package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.model.Operator;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;

public class DatabaseTableStation extends DatabaseTable
{
	private static final String TABLE_NAME = "Station";

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}

	public final List<Operator> selectOperators() throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT DISTINCT operator FROM Station";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<Operator> observedStations = new ArrayList<>();
			select(r -> Operator.getOperator(r).ifPresent(observedStations::add), preparedStatement);
			return observedStations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public final List<Operator> selectObservedOperators() throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT DISTINCT operator FROM Station WHERE observe = true;";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			final List<Operator> observedStations = new ArrayList<>();
			select(r -> Operator.getOperator(r).ifPresent(observedStations::add), preparedStatement);
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
			select(r -> StationDB.getStation(r).ifPresent(observedStations::add), preparedStatement);
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
			select(r -> StationDB.getStation(r).ifPresent(observedStations::add), preparedStatement);
			return observedStations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
