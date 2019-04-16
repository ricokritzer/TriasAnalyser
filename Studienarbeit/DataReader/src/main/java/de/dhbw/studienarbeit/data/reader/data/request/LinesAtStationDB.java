package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class LinesAtStationDB extends DB<Line> implements LinesAtStation
{
	@Override
	public final List<Line> getLinesAt(StationID stationID) throws IOException
	{
		final String sql = "SELECT DISTINCT name, destination, Stop.lineID "
				+ "FROM Stop, Line WHERE Stop.lineID = Line.lineID AND Stop.stationID = ?;";
		return readFromDatabase(sql, e -> setValues(e, stationID));
	}

	private void setValues(PreparedStatement preparedStatement, StationID stationID) throws SQLException
	{
		preparedStatement.setString(1, stationID.getValue());
	}

	@Override
	protected Optional<Line> getValue(ResultSet result) throws SQLException
	{
		final LineID lineID = new LineID(result.getInt("lineID"));
		final LineName name = new LineName(result.getString("name"));
		final LineDestination destination = new LineDestination(result.getString("destination"));

		return Optional.of(new Line(lineID, name, destination));
	}
}
