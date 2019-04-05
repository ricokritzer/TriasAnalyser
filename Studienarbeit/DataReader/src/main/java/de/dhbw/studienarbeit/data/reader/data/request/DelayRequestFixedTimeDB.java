package de.dhbw.studienarbeit.data.reader.data.request;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.SQLListHelper;

public class DelayRequestFixedTimeDB extends DelayRequestDB implements DelayRequestFixedTime
{
	private Optional<Date> timestampStart = Optional.empty();
	private Optional<Date> timestampEnd = Optional.empty();

	public DelayRequestFixedTimeDB(StationID stationID)
	{
		super(stationID);
	}

	@Override
	public void setTimestampStart(Optional<Date> date) throws InvalidTimeSpanException
	{
		if (possible(date, timestampEnd))
		{
			this.timestampStart = date;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	@Override
	public void setTimestampEnd(Optional<Date> date) throws InvalidTimeSpanException
	{
		if (possible(timestampStart, date))
		{
			this.timestampEnd = date;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	private boolean possible(Optional<Date> start, Optional<Date> end)
	{
		if (!start.isPresent())
		{
			return true;
		}

		if (!end.isPresent())
		{
			return true;
		}

		final Date s = start.get();
		final Date e = end.get();

		return !e.before(s);
	}

	@Override
	protected void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		int idx = 1;
		preparedStatement.setString(idx++, stationID.getValue());

		if (timestampStart.isPresent())
		{
			preparedStatement.setTimestamp(idx, new Timestamp(timestampStart.get().getTime()));
			idx++;
		}

		if (timestampEnd.isPresent())
		{
			preparedStatement.setTimestamp(idx, new Timestamp(timestampEnd.get().getTime()));
			idx++;
		}

		for (LineName lineName : lineNames)
		{
			preparedStatement.setString(idx, lineName.toString());
			idx++;
		}

		for (LineDestination lineDestination : lineDestinations)
		{
			preparedStatement.setString(idx, lineDestination.toString());
			idx++;
		}
	}

	@Override
	protected String getSQL(boolean realtimeNull)
	{
		final StringBuilder stringBuilder = new StringBuilder().append("SELECT ")
				.append("count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay")
				.append(" FROM Stop WHERE stationID = ?");

		timestampStart.ifPresent(h -> stringBuilder.append(" AND timetabledTime >= ?"));
		timestampEnd.ifPresent(h -> stringBuilder.append(" AND timetabledTime <= ?"));

		stringBuilder.append(SQLListHelper.createSQLFor(" AND name", lineNames));
		stringBuilder.append(SQLListHelper.createSQLFor(" AND destination", lineDestinations));

		return stringBuilder.append(realtimeNull ? " AND realtime IS NULL" : " AND realtime IS NOT NULL")
				.append(" GROUP BY delay;").toString();
	}
}
