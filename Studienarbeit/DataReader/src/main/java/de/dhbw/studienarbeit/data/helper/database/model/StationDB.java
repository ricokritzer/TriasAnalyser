package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

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

	public static final Optional<StationDB> getStation(ResultSet result)
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
}
