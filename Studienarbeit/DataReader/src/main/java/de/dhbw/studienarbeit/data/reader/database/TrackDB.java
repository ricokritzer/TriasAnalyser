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

public class TrackDB
{
	private static final Logger LOGGER = Logger.getLogger(TrackDB.class.getName());

	private final String station1;
	private final String station2;

	public TrackDB(String station1, String station2)
	{
		this.station1 = station1;
		this.station2 = station2;
	}

	public String getStation1()
	{
		return station1;
	}

	public String getStation2()
	{
		return station2;
	}

	private static final Optional<TrackDB> getTrack(ResultSet result)
	{
		try
		{
			final String station1 = result.getString("stationID1");
			final String station2 = result.getString("stationID2");

			return Optional.of(new TrackDB(station1, station2));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to track.", e);
			return Optional.empty();
		}
	}

	public static final List<TrackDB> getTracks() throws IOException
	{
		final String sql = "SELECT DISTINCT stationID1, stationID2 FROM StationNeighbour;";

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<TrackDB> list = new ArrayList<>();
			database.select(r -> TrackDB.getTrack(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
