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
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayRequestTimespanDB extends DB<DelayCountData> implements DelayRequestTimespan
{
	protected final StationID stationID;

	private Optional<Hour> hourStart = Optional.empty();
	private Optional<Hour> hourEnd = Optional.empty();

	private List<LineID> lineIDs = new ArrayList<>();
	private List<Weekday> weekdays = new ArrayList<>();

	public DelayRequestTimespanDB(StationID stationID)
	{
		this.stationID = stationID;
	}

	@Override
	public void setWeekday(Optional<Weekday> weekday)
	{
		weekday.ifPresent(this::addWeekday);

		if (!weekday.isPresent())
		{
			clearWeekdays();
		}
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
		lineID.ifPresent(lineIDs::add);

		if (!lineID.isPresent())
		{
			clearLines();
		}
	}

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

		for (Weekday weekday : weekdays)
		{
			preparedStatement.setInt(idx, weekday.getIdx());
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

		if (!weekdays.isEmpty())
		{
			stringBuilder.append(" AND WEEKDAY(timetabledTime) IN (?");

			for (int i = 1; i < weekdays.size(); i++)
			{
				stringBuilder.append(", ?");
			}

			stringBuilder.append(")");
		}

		hourStart.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) >= ?"));
		hourEnd.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) <= ?"));

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

	@Override
	public void addWeekday(Weekday weekday)
	{
		weekdays.add(weekday);
	}

	@Override
	public void removeWeekday(Weekday weekday)
	{
		weekdays.remove(weekday);
	}

	@Override
	public void clearWeekdays()
	{
		weekdays.clear();
	}

	@Override
	public void setLines(List<Line> lines)
	{
		clearLines();
		lines.forEach(this::addLine);
	}
}
