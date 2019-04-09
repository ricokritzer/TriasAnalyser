package de.dhbw.studienarbeit.data.reader.data.line;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayLineDB extends DB<DelayLineData> implements DelayLine
{
	public final List<DelayLineData> getDelays() throws IOException
	{
		final String sql = "SELECT name, destination, Stop.lineID, " + "count(*) AS total, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY Stop.lineID ORDER BY delay_avg DESC;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayLineData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));

		final LineID lineID = new LineID(result.getInt("lineID"));
		final LineName name = new LineName(result.getString("name"));
		final LineDestination destination = new LineDestination(result.getString("destination"));
		final CountData count = new CountData(result.getInt("total"));
		final Line line = new LineData(lineID, name, destination);

		return Optional.of(new DelayLineData(delayMaximum, delayAverage, count, line));
	}
}
