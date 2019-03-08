package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;
import de.dhbw.studienarbeit.data.reader.data.operator.Operator;

public class StationDB implements Saveable
{
	private static final Logger LOGGER = Logger.getLogger(StationDB.class.getName());

	private final String stationID;
	private final String name;
	private final double lat;
	private final double lon;
	private final String operator;
	private final boolean observe;

	public StationDB(String stationID, String name, double lat, double lon, String operator, boolean observe)
	{
		super();
		this.stationID = stationID;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.operator = operator;
		this.observe = observe;
	}

	public String getStationID()
	{
		return stationID;
	}

	public String getName()
	{
		return name;
	}

	public double getLat()
	{
		return lat;
	}

	public double getLon()
	{
		return lon;
	}

	public String getOperator()
	{
		return operator;
	}

	public boolean isObserve()
	{
		return observe;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Station VALUES(?, ?, ?, ?, ?, ?);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, stationID);
		preparedStatement.setString(2, name);
		preparedStatement.setDouble(3, lat);
		preparedStatement.setDouble(4, lon);
		preparedStatement.setString(5, operator);
		preparedStatement.setBoolean(6, observe);
	}

	private static final Optional<StationDB> getStation(ResultSet result)
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

	public static final List<StationDB> getObservedStations() throws IOException
	{
		final String sql = "SELECT * FROM Station WHERE observe = true;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<StationDB> list = new ArrayList<>();
			database.select(r -> StationDB.getStation(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static final List<StationDB> getObservedStations(Operator operator) throws IOException
	{
		final String sql = "SELECT * FROM Station WHERE observe = true AND operator = ?;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, operator.getName());

			final List<StationDB> list = new ArrayList<>();
			database.select(r -> StationDB.getStation(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
