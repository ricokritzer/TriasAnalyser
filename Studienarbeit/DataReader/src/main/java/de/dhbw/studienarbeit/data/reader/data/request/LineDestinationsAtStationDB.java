package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class LineDestinationsAtStationDB extends DB<LineDestination> implements LineDestinationsAtStation
{
	@Override
	public List<LineDestination> getLineDestinationsAt(StationID stationID, List<LineName> lineNames) throws IOException
	{
		final StringBuilder sb = new StringBuilder().append(
				"SELECT DISTINCT destination FROM Stop, Line WHERE Stop.lineID = Line.lineID AND Stop.stationID = ?");

		if (!lineNames.isEmpty())
		{
			sb.append(" AND name IN (?");

			for (int i = 1; i < lineNames.size(); i++)
			{
				sb.append(", ?");
			}

			sb.append(")");
		}

		sb.append(";");

		return readFromDatabase(sb.toString(), e -> setValues(e, stationID, lineNames));
	}

	private void setValues(PreparedStatement preparedStatement, StationID stationID, List<LineName> names)
			throws SQLException
	{
		preparedStatement.setString(1, stationID.getValue());

		int i = 1;
		for (LineName name : names)
		{
			preparedStatement.setString(i, name.toString());
			i++;
		}
	}

	@Override
	protected Optional<LineDestination> getValue(ResultSet result) throws SQLException
	{
		return Optional.ofNullable(new LineDestination(result.getString("destination")));
	}
}
