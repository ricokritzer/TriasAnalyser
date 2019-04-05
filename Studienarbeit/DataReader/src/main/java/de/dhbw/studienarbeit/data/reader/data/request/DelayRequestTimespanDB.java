package de.dhbw.studienarbeit.data.reader.data.request;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class DelayRequestTimespanDB extends DelayRequestDB implements DelayRequestTimespan
{
	private Optional<Hour> hourStart = Optional.empty();
	private Optional<Hour> hourEnd = Optional.empty();

	private List<Weekday> weekdays = new ArrayList<>();

	public DelayRequestTimespanDB(StationID stationID)
	{
		super(stationID);
	}

	@Override
	public void setWeekdays(Collection<Weekday> weekday)
	{
		clearWeekdays();
		weekday.forEach(weekdays::add);
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

	@Override
	protected void setValues(PreparedStatement preparedStatement) throws SQLException
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

		// for (LineID lineID : lineIDs)
		// {
		// preparedStatement.setInt(idx, lineID.getValue());
		// idx++;
		// }
	}

	@Override
	protected String getSQL(boolean realtimeNull)
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

		// if (!lineIDs.isEmpty())
		// {
		// stringBuilder.append(" AND lineID IN (?");
		//
		// for (int i = 1; i < lineIDs.size(); i++)
		// {
		// stringBuilder.append(", ?");
		// }
		//
		// stringBuilder.append(")");
		// }

		return stringBuilder.append(realtimeNull ? " AND realtime IS NULL" : " AND realtime IS NOT NULL")
				.append(" GROUP BY delay;").toString();
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
}
