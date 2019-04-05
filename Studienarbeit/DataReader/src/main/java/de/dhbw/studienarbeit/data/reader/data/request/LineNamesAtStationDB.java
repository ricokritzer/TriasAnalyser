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
import de.dhbw.studienarbeit.data.reader.database.SQLListHelper;

public class LineNamesAtStationDB extends DB<LineName> implements LineNamesAtStation
{
	@Override
	public List<LineName> getLineNamesAt(StationID stationID, List<LineDestination> lineDestinations) throws IOException
	{
		final StringBuilder sb = new StringBuilder()
				.append("SELECT DISTINCT name FROM Stop, Line WHERE Stop.lineID = Line.lineID AND Stop.stationID = ?");

		sb.append(SQLListHelper.createSQLFor(" AND destination", lineDestinations));
		sb.append(";");

		return readFromDatabase(sb.toString(), e -> setValues(e, stationID, lineDestinations));
	}

	private void setValues(PreparedStatement preparedStatement, StationID stationID, List<LineDestination> destinations)
			throws SQLException
	{
		preparedStatement.setString(1, stationID.getValue());

		int i = 2;
		for (LineDestination destination : destinations)
		{
			preparedStatement.setString(i, destination.toString());
			i++;
		}
	}

	@Override
	protected Optional<LineName> getValue(ResultSet result) throws SQLException
	{
		return Optional.ofNullable(new LineName(result.getString("name")));
	}
}
