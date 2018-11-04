package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
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

	public final List<StationDB> selectStations(SqlCondition... conditions) throws IOException
	{
		try
		{
			final List<StationDB> stations = new ArrayList<>();
			select(r -> getStation(r).ifPresent(stations::add), TABLE_NAME, conditions);
			return stations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting stations does not succeed.", e);
		}
	}

	public final List<StationDB> selectObservedStations() throws IOException
	{
		return selectStations(new SqlCondition("observe", true));
	}
}
