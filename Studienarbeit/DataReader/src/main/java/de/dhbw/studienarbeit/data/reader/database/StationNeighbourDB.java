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

import de.dhbw.studienarbeit.data.reader.data.StationNeighbourData;

public class StationNeighbourDB implements StationNeighbourData
{
	private static final Logger LOGGER = Logger.getLogger(StationNeighbourDB.class.getName());

	private final String station1;
	private final double lat1;
	private final double lon1;

	private final String station2;
	private final double lat2;
	private final double lon2;

	public StationNeighbourDB(String station1, double lat1, double lon1, String station2, double lat2, double lon2)
	{
		this.station1 = station1;
		this.lat1 = lat1;
		this.lon1 = lon1;
		this.station2 = station2;
		this.lat2 = lat2;
		this.lon2 = lon2;
	}

	public String getStation1()
	{
		return station1;
	}

	public double getLat1()
	{
		return lat1;
	}

	public double getLon1()
	{
		return lon1;
	}

	public String getStation2()
	{
		return station2;
	}

	public double getLat2()
	{
		return lat2;
	}

	public double getLon2()
	{
		return lon2;
	}

	private static final Optional<StationNeighbourDB> getTrack(ResultSet result)
	{
		try
		{
			final String station1 = result.getString("stationID1");
			final double lat1 = result.getDouble("lat1");
			final double lon1 = result.getDouble("lon1");
			final String station2 = result.getString("stationID2");
			final double lat2 = result.getDouble("lat2");
			final double lon2 = result.getDouble("lon2");

			return Optional.of(new StationNeighbourDB(station1, lat1, lon1, station2, lat2, lon2));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to track.", e);
			return Optional.empty();
		}
	}

	public static final List<StationNeighbourDB> getTracks() throws IOException
	{
		final String sql = "SELECT DISTINCT stationID1, station1.lat AS lat1, station1.lon AS lon1, "
				+ "stationID2, station2.lat AS lat2, station2.lon AS lon2 "
				+ "FROM StationNeighbour, Station station1, Station station2 "
				+ "WHERE StationNeighbour.stationID1 = station1.stationID AND StationNeighbour.stationID2 = station2.stationID;";

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<StationNeighbourDB> list = new ArrayList<>();
			database.select(r -> StationNeighbourDB.getTrack(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
