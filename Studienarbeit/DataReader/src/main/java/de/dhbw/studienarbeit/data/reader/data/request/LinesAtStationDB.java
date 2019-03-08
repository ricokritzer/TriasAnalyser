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

import de.dhbw.studienarbeit.data.reader.data.line.LineData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class LinesAtStationDB implements LineData
{
	private static final Logger LOGGER = Logger.getLogger(LinesAtStationDB.class.getName());

	private final LineID lineID;
	private final LineName lineName;
	private final LineDestination lineDestination;

	public LinesAtStationDB(LineID lineID, LineName lineName, LineDestination lineDestination)
	{
		this.lineID = lineID;
		this.lineName = lineName;
		this.lineDestination = lineDestination;
	}

	private static final Optional<LineData> getLines(ResultSet result)
	{
		try
		{
			final LineID lineID = new LineID(result.getInt("lineID"));
			final LineName name = new LineName(result.getString("name"));
			final LineDestination destination = new LineDestination(result.getString("destination"));

			return Optional.of(new LinesAtStationDB(lineID, name, destination));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public static final List<LineData> getLinesAt(StationID stationID) throws IOException
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

	@Override
	public LineID getID()
	{
		return lineID;
	}

	@Override
	public LineName getName()
	{
		return lineName;
	}

	@Override
	public LineDestination getDestination()
	{
		return lineDestination;
	}

	@Override
	public String getLineName()
	{
		return lineName.getValue();
	}

	@Override
	public String getLineDestination()
	{
		return lineDestination.getValue();
	}
}
