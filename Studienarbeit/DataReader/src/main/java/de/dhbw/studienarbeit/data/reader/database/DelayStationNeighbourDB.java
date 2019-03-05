package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbourData;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourData;

public class DelayStationNeighbourDB implements Comparable<DelayStationNeighbourDB>, DelayStationNeighbourData
{
	private static final Logger LOGGER = Logger.getLogger(DelayStationNeighbourDB.class.getName());

	private final String stationName1;
	private final double lat1;
	private final double lon1;
	private final double avg1;

	private final String stationName2;
	private final double lat2;
	private final double lon2;
	private final double avg2;

	public DelayStationNeighbourDB(String stationName1, double lat1, double lon1, double avg1, String stationName2,
			double lat2, double lon2, double avg2)
	{
		this.stationName1 = stationName1;
		this.lat1 = lat1;
		this.lon1 = lon1;
		this.avg1 = avg1;
		this.stationName2 = stationName2;
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

	public static List<DelayStationNeighbourDB> convertToStationNeighbours(final List<StationNeighbourData> tracks)
	{
		final List<DelayStationNeighbourDB> list = new ArrayList<>();
		tracks.forEach(t -> convertToStationNeighbour(t).ifPresent(list::add));
		return list;
	}

	public static Optional<DelayStationNeighbourDB> convertToStationNeighbour(
			final StationNeighbourData stationNeighbour)
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
			preparedStatement.setString(1, stationNeighbour.getStationID1());
			preparedStatement.setString(2, stationNeighbour.getStationID2());
			preparedStatement.setString(3, stationNeighbour.getStationID1());

			final List<Double> list = new ArrayList<>();
			database.select(r -> DelayStationNeighbourDB.getDelay(r).ifPresent(list::add), preparedStatement);

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
			preparedStatement.setString(1, stationNeighbour.getStationID1());
			preparedStatement.setString(2, stationNeighbour.getStationID2());
			preparedStatement.setString(3, stationNeighbour.getStationID2());

			final List<Double> list = new ArrayList<>();
			database.select(r -> DelayStationNeighbourDB.getDelay(r).ifPresent(list::add), preparedStatement);

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

		return Optional.of(new DelayStationNeighbourDB(stationNeighbour.getStationName1(), stationNeighbour.getLat1(),
				stationNeighbour.getLon1(), avg1, stationNeighbour.getStationName2(), stationNeighbour.getLat2(),
				stationNeighbour.getLon2(), avg2));
	}

	@Override
	public int compareTo(DelayStationNeighbourDB o)
	{
		return Double.compare(this.getSlope(), o.getSlope());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DelayStationNeighbourDB)
		{
			final DelayStationNeighbourDB o = (DelayStationNeighbourDB) obj;
			return o.getLat1() == this.getLat1() && o.getLon1() == this.getLon1() //
					&& o.getLat2() == this.getLat2() && o.getLon2() == this.getLon2();
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(lat1, lon1, lat2, lon2);
	}

	@Override
	public String getStationName1()
	{
		return stationName1;
	}

	@Override
	public String getStationName2()
	{
		return stationName2;
	}
}
