package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayRequestFixedTimeDB extends DB<DelayCountData> implements DelayRequestFixedTime
{
	protected final StationID stationID;

	private Optional<Date> timestampStart = Optional.empty();
	private Optional<Date> timestampEnd = Optional.empty();

	private List<LineID> lineIDs = new ArrayList<>();

	public DelayRequestFixedTimeDB(StationID stationID)
	{
		this.stationID = stationID;
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
	public final CountData getCancelledStops() throws IOException
	{
		final List<DelayCountData> data = readFromDatabase(getCancelledSQL(), this::setValues);

		if (data.isEmpty())
		{
			return CountData.UNABLE_TO_COUNT;
		}

		return data.get(0).getCount();
	}

	private void setValues(PreparedStatement preparedStatement) throws SQLException
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

		for (LineID lineID : lineIDs)
		{
			preparedStatement.setInt(idx, lineID.getValue());
			idx++;
		}
	}

	protected String getDelaySQL()
	{
		return getSQL(false);
	}

	protected String getCancelledSQL()
	{
		return getSQL(true);
	}

	private String getSQL(boolean realtimeNull)
	{
		final StringBuilder stringBuilder = new StringBuilder().append("SELECT ")
				.append("count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay")
				.append(" FROM Stop WHERE stationID = ?");

		timestampStart.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) >= ?"));
		timestampEnd.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) <= ?"));

		if (!lineIDs.isEmpty())
		{
			stringBuilder.append(" AND lineID IN (?");

			for (int i = 1; i < lineIDs.size(); i++)
			{
				stringBuilder.append(", ?");
			}

			stringBuilder.append(")");
		}

		return stringBuilder.append(realtimeNull ? " AND realtime IS NULL" : " AND realtime IS NOT NULL")
				.append(" GROUP BY delay;").toString();
	}

	@Override
	protected Optional<DelayCountData> getValue(ResultSet result) throws SQLException
	{
		final Delay delay = new Delay(result.getDouble("delay"));
		final CountData count = new CountData(result.getLong("total"));

		return Optional.of(new DelayCountData(delay, count));
	}

	@Override
	public List<DelayCountData> getDelayCounts() throws IOException
	{
		return readFromDatabase(getDelaySQL(), this::setValues);
	}

	@Override

	public void addLine(Line line)
	{
		lineIDs.add(line.getID());
	}

	@Override
	public void removeLine(Line line)
	{
		lineIDs.remove(line.getID());
	}

	@Override
	public void clearLines()
	{
		lineIDs.clear();
	}
}
