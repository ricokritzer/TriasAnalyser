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

public class StationNeighbourDB implements Comparable<StationNeighbourDB>
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

	private static final Optional<Double> getDelay(ResultSet result)
	{
		try
		{
			final double delay = result.getDouble("delay");

			return Optional.of(Double.valueOf(delay));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to station.", e);
			return Optional.empty();
		}
	}

	public static List<StationNeighbourDB> convertToStationNeighbours(final List<TrackDB> tracks)
	{
		final List<StationNeighbourDB> list = new ArrayList<>();
		tracks.forEach(t -> convertToStationNeighbour(t).ifPresent(list::add));
		return list;
	}

	public static Optional<StationNeighbourDB> convertToStationNeighbour(final TrackDB track)
	{
		double avg1 = 0.0;
		double avg2 = 0.0;

		final String sql = "SELECT avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay "
				+ "FROM Stop "
				+ "WHERE Stop.lineID in (SELECT LineId FROM StationNeighbour WHERE stationID1 = ? AND stationID2 = ?) "
				+ "AND Stop.stationID = ?";

		final DatabaseReader database = new DatabaseReader();

		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, track.getStation1());
			preparedStatement.setString(2, track.getStation2());
			preparedStatement.setString(3, track.getStation1());

			final List<Double> list = new ArrayList<>();
			database.select(r -> StationNeighbourDB.getDelay(r).ifPresent(list::add), preparedStatement);

			if (list.size() == 1)
			{
				avg1 = list.get(0).doubleValue();
			}
		}
		catch (SQLException | IOException e)
		{
			LOGGER.log(Level.WARNING, "Selecting does not succeed.", e);
			return Optional.empty();
		}

		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, track.getStation1());
			preparedStatement.setString(2, track.getStation2());
			preparedStatement.setString(3, track.getStation2());

			final List<Double> list = new ArrayList<>();
			database.select(r -> StationNeighbourDB.getDelay(r).ifPresent(list::add), preparedStatement);

			if (list.size() == 1)
			{
				avg2 = list.get(0).doubleValue();
			}
		}
		catch (SQLException | IOException e)
		{
			LOGGER.log(Level.WARNING, "Selecting does not succeed.", e);
			return Optional.empty();
		}

		return Optional.of(
				new StationNeighbourDB(track.getLat1(), track.getLon1(), avg1, track.getLat2(), track.getLat2(), avg2));
	}

	@Override
	public int compareTo(StationNeighbourDB o)
	{
		return Double.compare(this.getSlope(), o.getSlope());
	}

	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
}
