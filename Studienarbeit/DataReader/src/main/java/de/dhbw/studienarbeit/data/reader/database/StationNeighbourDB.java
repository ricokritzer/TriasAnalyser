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

import de.dhbw.studienarbeit.data.reader.data.station.OperatorName;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourData;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourPart;

public class StationNeighbourDB implements StationNeighbourData
{
	private static final Logger LOGGER = Logger.getLogger(StationNeighbourDB.class.getName());

	private final StationNeighbourPart stationFrom;
	private final StationNeighbourPart stationTo;

	public StationNeighbourDB(StationNeighbourPart stationFrom, StationNeighbourPart stationTo)
	{
		this.stationFrom = stationFrom;
		this.stationTo = stationTo;
	}

	public String getStationID1()
	{
		return stationFrom.getStationID().getValue();
	}

	public String getStationID2()
	{
		return stationTo.getStationID().getValue();
	}

	private static final Optional<StationNeighbourDB> getTrack(ResultSet result)
	{
		try
		{
			final StationID station1 = new StationID(result.getString("stationID1"));
			final StationName stationName1 = new StationName(result.getString("name1"));
			final Position position1 = new Position(result.getDouble("lat1"), result.getDouble("lon1"));
			final OperatorName operator1 = new OperatorName("operator1");
			final StationNeighbourPart stationFrom = new StationNeighbourPart(station1, stationName1, position1,
					operator1);

			final StationID station2 = new StationID(result.getString("stationID2"));
			final StationName stationName2 = new StationName(result.getString("name2"));
			final Position position2 = new Position(result.getDouble("lat2"), result.getDouble("lon2"));
			final OperatorName operator2 = new OperatorName("operator2");
			final StationNeighbourPart stationTo = new StationNeighbourPart(station2, stationName2, position2,
					operator2);

			return Optional.of(new StationNeighbourDB(stationFrom, stationTo));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to track.", e);
			return Optional.empty();
		}
	}

	public static final List<StationNeighbourData> getStationNeighbours() throws IOException
	{
		final String sql = "SELECT DISTINCT "
				+ "stationID1, station1.lat AS lat1, station1.lon AS lon1, station1.name AS name1, station1.operator AS operator1, "
				+ "stationID2, station2.lat AS lat2, station2.lon AS lon2, station2.name AS name2, station2.operator AS operator2 "
				+ "FROM StationNeighbour, Station station1, Station station2 "
				+ "WHERE StationNeighbour.stationID1 = station1.stationID AND StationNeighbour.stationID2 = station2.stationID;";

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<StationNeighbourData> list = new ArrayList<>();
			database.select(r -> StationNeighbourDB.getTrack(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	@Override
	public StationNeighbourPart getStationFrom()
	{
		return stationFrom;
	}

	@Override
	public StationNeighbourPart getStationTo()
	{
		return stationTo;
	}
}
