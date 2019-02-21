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

public class StationNeighbourDB
{
	private static final Logger LOGGER = Logger.getLogger(StationNeighbourDB.class.getName());
	private final double lat1;
	private final double lon1;
	private final double avg1;
	private final double lat2;
	private final double lon2;
	private final double avg2;

	public StationNeighbourDB(double lat1, double lon1, double avg1, double lat2, double lon2, double avg2)
	{
		this.lat1 = lat1;
		this.lon1 = lon1;
		this.avg1 = avg1;
		this.lat2 = lat2;
		this.lon2 = lon2;
		this.avg2 = avg2;
	}

	public double getLat1()
	{
		return lat1;
	}

	public double getLon1()
	{
		return lon1;
	}

	public double getAvg1()
	{
		return avg1;
	}

	public double getLat2()
	{
		return lat2;
	}

	public double getLon2()
	{
		return lon2;
	}

	public double getAvg2()
	{
		return avg2;
	}

	public double getSlope()
	{
		final double distanceLat = lat1 - lat2;
		final double distanceLon = lon1 - lon2;
		final double distance = Math.sqrt(Math.pow(distanceLat, 2) + Math.pow(distanceLon, 2));
		final double delayDifference = Math.abs(avg1 - avg2);

		return delayDifference / distance;
	}

	private static final Optional<StationNeighbourDB> getStation(ResultSet result)
	{
		try
		{
			final double lat1 = result.getDouble("lat1");
			final double lon1 = result.getDouble("lon1");
			final double delay_avg1 = result.getDouble("delay_avg1");
			final double lat2 = result.getDouble("lat2");
			final double lon2 = result.getDouble("lon2");
			final double delay_avg2 = result.getDouble("delay_avg2");

			return Optional.of(new StationNeighbourDB(lat1, lon1, delay_avg1, lat2, lon2, delay_avg2));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to station.", e);
			return Optional.empty();
		}
	}

	public static final List<StationNeighbourDB> getStationNeighbours() throws IOException
	{
		final String sql = "SELECT station1.lat AS lat1, station2.lat AS lat2, station1.lon AS lon1, station2.lon AS lon2, "
				+ "avg(UNIX_TIMESTAMP(stop1.realTime) - UNIX_TIMESTAMP(stop1.timeTabledTime)) AS delay_avg1, "
				+ "avg(UNIX_TIMESTAMP(stop2.realTime) - UNIX_TIMESTAMP(stop2.timeTabledTime)) AS delay_avg2 "
				+ "FROM StationNeighbour, Stop stop1, Stop stop2, Station station1, Station station2 "
				+ "WHERE StationNeighbour.lineID = stop1.lineID AND StationNeighbour.lineID = stop2.lineID "
				+ "AND StationNeighbour.stationID1 = stop1.stationID AND StationNeighbour.stationID2 = stop2.stationID "
				+ "AND StationNeighbour.stationID1 = station1.stationID AND StationNeighbour.stationID2 = station2.stationID "
				+ "GROUP BY lat1, lat2, lon1, lon2;";

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<StationNeighbourDB> list = new ArrayList<>();
			database.select(r -> StationNeighbourDB.getStation(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
