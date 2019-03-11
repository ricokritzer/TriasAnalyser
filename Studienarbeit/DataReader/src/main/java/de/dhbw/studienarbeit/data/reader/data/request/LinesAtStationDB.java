package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class LinesAtStationDB implements LinesAtStation
{
	private static final Logger LOGGER = Logger.getLogger(LinesAtStationDB.class.getName());

	private static final Optional<LineData> getLines(ResultSet result)
	{
		try
		{
			final LineID lineID = new LineID(result.getInt("lineID"));
			final LineName name = new LineName(result.getString("name"));
			final LineDestination destination = new LineDestination(result.getString("destination"));

			return Optional.of(new Line(lineID, name, destination));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	@Override
	public final List<LineData> getLinesAt(StationID stationID) throws IOException
	{
		final String sql = "SELECT DISTINCT name, destination, Stop.lineID "
				+ "FROM Stop, Line WHERE Stop.lineID = Line.lineID AND Stop.stationID = ?;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, stationID.getValue());

			final List<LineData> list = new ArrayList<>();
			database.select(r -> getLines(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
