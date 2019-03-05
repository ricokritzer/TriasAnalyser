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

import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourData;

public class StationNeighbourDB implements StationNeighbourData
{
	private static final Logger LOGGER = Logger.getLogger(StationNeighbourDB.class.getName());

	private final StationID station1;
	private final StationName stationName1;
	private final Position position1;

	private final StationID station2;
	private final StationName stationName2;
	private final Position position2;

	public StationNeighbourDB(StationID station1, StationName stationName1, Position position1, StationID station2,
			StationName stationName2, Position position2)
	{
		this.station1 = station1;
		this.stationName1 = stationName1;
		this.position1 = position1;

		this.station2 = station2;
		this.stationName2 = stationName2;
		this.position2 = position2;
	}

	public String getStationID1()
	{
		return station1.getValue();
	}

	public double getLat1()
	{
		return position1.getLat();
	}

	public double getLon1()
	{
		return position1.getLon();
	}

	public String getStationID2()
	{
		return station2.getValue();
	}

	public double getLat2()
	{
		return position2.getLat();
	}

	public double getLon2()
	{
		return position2.getLon();
	}

	private static final Optional<StationNeighbourDB> getTrack(ResultSet result)
	{
		try
		{
			final StationID station1 = new StationID(result.getString("stationID1"));
			final StationName stationName1 = new StationName(result.getString("name1"));
			final Position position1 = new Position(result.getDouble("lat1"), result.getDouble("lon1"));

			final StationID station2 = new StationID(result.getString("stationID2"));
			final StationName stationName2 = new StationName(result.getString("name2"));
			final Position position2 = new Position(result.getDouble("lat2"), result.getDouble("lon2"));

			return Optional
					.of(new StationNeighbourDB(station1, stationName1, position1, station2, stationName2, position2));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to track.", e);
			return Optional.empty();
		}
	}

	public static final List<StationNeighbourDB> getTracks() throws IOException
	{
		final String sql = "SELECT DISTINCT "
				+ "stationID1, station1.lat AS lat1, station1.lon AS lon1, station1.name AS name1, "
				+ "stationID2, station2.lat AS lat2, station2.lon AS lon2, station2.name AS name2 "
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

	@Override
	public String getStationName1()
	{
		return stationName1.getStationName();
	}

	@Override
	public String getStationName2()
	{
		return stationName2.getStationName();
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
