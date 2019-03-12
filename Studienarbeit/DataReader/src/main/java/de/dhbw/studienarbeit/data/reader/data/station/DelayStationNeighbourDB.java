package de.dhbw.studienarbeit.data.reader.data.station;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayStationNeighbourDB implements DelayStationNeighbour
{
	private static final Logger LOGGER = Logger.getLogger(DelayStationNeighbourDB.class.getName());

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

	public List<DelayStationNeighbourData> convertToStationNeighbours(final List<StationNeighbourData> tracks)
	{
		final List<DelayStationNeighbourData> list = new ArrayList<>();
		tracks.forEach(t -> convertToStationNeighbour(t).ifPresent(list::add));
		return list;
	}

	public Optional<DelayStationNeighbourData> convertToStationNeighbour(final StationNeighbourData stationNeighbour)
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

		final StationData from = stationNeighbour.getStationFrom();
		final StationData to = stationNeighbour.getStationTo();

		return Optional.of(new DelayStationNeighbourData(from.getName(), from.getPosition(), avg1, to.getName(),
				to.getPosition(), avg2));
	}
}
