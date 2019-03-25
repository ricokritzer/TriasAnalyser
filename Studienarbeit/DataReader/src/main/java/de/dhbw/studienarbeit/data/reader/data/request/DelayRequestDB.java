package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.data.reader.database.DB;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayRequestDB extends DB<DelayCountData> implements DelayRequest
{
	protected final StationID stationID;

	private Optional<Weekday> weekday = Optional.empty();
	private Optional<Hour> hourStart = Optional.empty();
	private Optional<Hour> hourEnd = Optional.empty();
	private Optional<LineID> lineID = Optional.empty();

	public DelayRequestDB(StationID stationID)
	{
		this.stationID = stationID;
	}

	public void setWeekday(Optional<Weekday> weekday)
	{
		this.weekday = weekday;
	}

	@Override
	public void setHourStart(Optional<Hour> hour) throws InvalidTimeSpanException
	{
		if (possible(hour, hourEnd))
		{
			this.hourStart = hour;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	@Override
	public void setHourEnd(Optional<Hour> hour) throws InvalidTimeSpanException
	{
		if (possible(hourStart, hour))
		{
			this.hourEnd = hour;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	private boolean possible(Optional<Hour> start, Optional<Hour> end)
	{
		if (!start.isPresent())
		{
			return true;
		}

		if (!end.isPresent())
		{
			return true;
		}

		final Hour s = start.get();
		final Hour e = end.get();

		return !e.before(s);
	}

	public void setLineID(Optional<LineID> lineID)
	{
		this.lineID = lineID;
	}

	public void setLineID(LineID lineID)
	{
		this.lineID = Optional.ofNullable(lineID);
	}

	public final CountData getCancelledStops() throws IOException
	{
		final String sql = getCancelledSQL();
		return new DatabaseReader().count(sql);
	}

	private void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		int idx = 1;
		preparedStatement.setString(idx++, stationID.getValue());

		if (weekday.isPresent())
		{
			preparedStatement.setInt(idx, weekday.get().getIdx());
			idx++;
		}

		if (hourStart.isPresent())
		{
			preparedStatement.setInt(idx, hourStart.get().getValue());
			idx++;
		}

		if (hourEnd.isPresent())
		{
			preparedStatement.setInt(idx, hourEnd.get().getValue());
			idx++;
		}

		if (lineID.isPresent())
		{
			preparedStatement.setInt(idx, lineID.get().getValue());
		}
	}

	/*
	 * use Data.getDelayCounts() instead. The method will allways return empty List!
	 */
	@Deprecated
	public final List<Delay> getDelays() throws IOException
	{
		return new ArrayList<>();
	}

	protected String getDelaySQL()
	{
		return getSQL("(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay",
				" AND realtime IS NOT NULL GROUP BY delay;");
	}

	protected String getCancelledSQL()
	{
		return getSQL("count(*) AS total", " AND realtime IS NULL;");
	}

	private String getSQL(String what, String additional)
	{
		final StringBuilder stringBuilder = new StringBuilder().append("SELECT ").append(what)
				.append(" FROM Stop WHERE stationID = ?");

		weekday.ifPresent(w -> stringBuilder.append(" AND WEEKDAY(timetabledTime) = ?"));
		hourStart.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) >= ?"));
		hourEnd.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) <= ?"));
		lineID.ifPresent(h -> stringBuilder.append(" AND lineID = ?"));

		return stringBuilder.append(additional).toString();
	}

	@Override
	protected Optional<DelayCountData> getValue(ResultSet result) throws SQLException
	{
		final Delay delay = new Delay(result.getDouble("delay"));
		final CountData count = new CountData(result.getLong("count"));

		return Optional.of(new DelayCountData(delay, count));
	}

	@Override
	public List<DelayCountData> getDelayCounts() throws IOException
	{
		final String sql = getDelaySQL();
		return readFromDatabase(sql, this::setValues);
	}
}
