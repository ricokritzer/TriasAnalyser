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

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.database.DB;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayStationNeighbourDB extends DB<DelayAverage> implements DelayStationNeighbour
{
	private static final Logger LOGGER = Logger.getLogger(DelayStationNeighbourDB.class.getName());

	public List<DelayStationNeighbourData> convertToStationNeighbours(final List<StationNeighbourData> tracks)
	{
		final List<DelayStationNeighbourData> list = new ArrayList<>();
		tracks.forEach(t -> convertToStationNeighbour(t).ifPresent(list::add));
		return list;
	}

	private Optional<DelayAverage> getDelay(StationData stationFrom, StationData stationTo,
			StationData requestedStation)
	{
		final String sql = "SELECT avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay "
				+ "FROM Stop "
				+ "WHERE Stop.lineID in (SELECT LineId FROM StationNeighbour WHERE stationID1 = ? AND stationID2 = ?) "
				+ "AND Stop.stationID = ? HAVING count(Stop.realTime) > 1";

		final DatabaseReader database = new DatabaseReader();

		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, stationFrom.getStationID().getValue());
			preparedStatement.setString(2, stationTo.getStationID().getValue());
			preparedStatement.setString(3, requestedStation.getStationID().getValue());

			final List<DelayAverage> list = new ArrayList<>();
			database.select(r -> parse(r).ifPresent(list::add), preparedStatement);

			if (list.isEmpty())
			{
				return Optional.empty();
			}
			return Optional.ofNullable(list.get(0));
		}
		catch (SQLException | IOException e)
		{
			LOGGER.log(Level.WARNING, "Selecting does not succeed.", e);
			return Optional.empty();
		}
	}

	public Optional<DelayStationNeighbourData> convertToStationNeighbour(final StationNeighbourData stationNeighbour)
	{
		final StationData from = stationNeighbour.getStationFrom();
		final StationData to = stationNeighbour.getStationTo();

		final Optional<DelayAverage> avg1 = getDelay(from, to, from);
		if (!avg1.isPresent())
		{
			return Optional.empty();
		}

		final Optional<DelayAverage> avg2 = getDelay(from, to, to);
		if (!avg2.isPresent())
		{
			return Optional.empty();
		}

		return Optional.of(new DelayStationNeighbourData(from.getName(), from.getPosition(), avg1.get(), to.getName(),
				to.getPosition(), avg2.get()));
	}

	@Override
	protected Optional<DelayAverage> getValue(ResultSet result) throws SQLException
	{
		return Optional.of(new DelayAverage(result.getDouble("delay")));
	}
}
