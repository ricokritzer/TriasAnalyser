package de.dhbw.studienarbeit.data.reader.data.line;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.DelayDB;

public class DelayLineDB extends DelayDB<Line>
{
	@Override
	protected Line getElement(ResultSet result) throws SQLException
	{
		final LineID lineID = new LineID(result.getInt("lineID"));
		final LineName name = new LineName(result.getString("name"));
		final LineDestination destination = new LineDestination(result.getString("destination"));

		return new Line(lineID, name, destination);
	}

	@Override
	protected String getSQL()
	{
		return "SELECT name, destination, Stop.lineID, " + "count(*) AS total, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY Stop.lineID ORDER BY delay_avg DESC;";
	}
}
