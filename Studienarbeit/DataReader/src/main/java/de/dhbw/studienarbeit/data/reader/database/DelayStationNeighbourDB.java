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
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourData;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourPart;

public class DelayStationNeighbourDB implements Comparable<DelayStationNeighbourDB>, DelayStationNeighbourData
{
	private static final Logger LOGGER = Logger.getLogger(DelayStationNeighbourDB.class.getName());

	private final StationName stationName1;
	private final Position position1;
	private final double avg1;

	private final StationName stationName2;
	private final Position position2;
	private final double avg2;

	public DelayStationNeighbourDB(StationName stationName1, Position position1, double avg1, StationName stationName2,
			Position position2, double avg2)
	{
		this.stationName1 = stationName1;
		this.position1 = position1;
		this.avg1 = avg1;
		this.stationName2 = stationName2;
		this.position2 = position2;
		this.avg2 = avg2;
	}

	public double getLat1()
	{
		return position1.getLat();
	}

	public double getLon1()
	{
		return position1.getLon();
	}

	public double getAvg1()
	{
		return avg1;
	}

	public double getLat2()
	{
		return position2.getLat();
	}

	public double getLon2()
	{
		return position2.getLon();
	}

	public double getAvg2()
	{
		return avg2;
	}

	public double getSlope()
	{
		final double distance = Position.getDistance(position1, position2);
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

	public static List<DelayStationNeighbourData> convertToStationNeighbours(final List<StationNeighbourData> tracks)
	{
		final List<DelayStationNeighbourData> list = new ArrayList<>();
		tracks.forEach(t -> convertToStationNeighbour(t).ifPresent(list::add));
		return list;
	}

	public static Optional<DelayStationNeighbourData> convertToStationNeighbour(
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
			preparedStatement.setString(1, stationNeighbour.getStationFrom().getStationID().getValue());
			preparedStatement.setString(2, stationNeighbour.getStationTo().getStationID().getValue());
			preparedStatement.setString(3, stationNeighbour.getStationFrom().getStationID().getValue());

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
			preparedStatement.setString(1, stationNeighbour.getStationFrom().getStationID().getValue());
			preparedStatement.setString(2, stationNeighbour.getStationTo().getStationID().getValue());
			preparedStatement.setString(3, stationNeighbour.getStationTo().getStationID().getValue());

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

		final StationNeighbourPart from = stationNeighbour.getStationFrom();
		final StationNeighbourPart to = stationNeighbour.getStationTo();

		return Optional.of(new DelayStationNeighbourDB(from.getName(), from.getPosition(), avg1, to.getName(),
				to.getPosition(), avg2));
	}

	@Override
	public int compareTo(DelayStationNeighbourDB o)
	{
		return Double.compare(this.getSlope(), o.getSlope());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DelayStationNeighbourData)
		{
			final DelayStationNeighbourData o = (DelayStationNeighbourData) obj;
			return o.getPosition1().equals(this.position1) && o.getPosition2().equals(this.position2);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(position1, position2);
	}

	@Override
	public StationName getName1()
	{
		return stationName1;
	}

	@Override
	public Position getPosition1()
	{
		return position1;
	}

	@Override
	public StationName getName2()
	{
		return stationName2;
	}

	@Override
	public Position getPosition2()
	{
		return position2;
	}
}
