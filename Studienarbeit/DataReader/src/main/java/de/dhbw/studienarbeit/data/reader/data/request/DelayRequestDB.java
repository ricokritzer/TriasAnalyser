package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.TimeSpan;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.data.reader.database.DB;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayRequestDB extends DB<Delay> implements DelayRequest
{
	protected final StationID stationID;

	private Optional<Weekday> weekday = Optional.empty();
	private Optional<TimeSpan> hour = Optional.empty();
	private Optional<LineID> lineID = Optional.empty();

	public DelayRequestDB(StationID stationID)
	{
		this.stationID = stationID;
	}

	public void setWeekday(Weekday weekday)
	{
		this.weekday = Optional.ofNullable(weekday);
	}

	public void setWeekday(Optional<Weekday> weekday)
	{
		this.weekday = weekday;
	}

	public void setHour(Optional<TimeSpan> hour)
	{
		this.hour = hour;
	}

	public void setHour(TimeSpan hour)
	{
		this.hour = Optional.ofNullable(hour);
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
		final String sql = getSQL("count(*) AS total") + " AND realTime IS NULL;";
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
		if (hour.isPresent())
		{
			preparedStatement.setInt(idx, hour.get().getIdx());
			idx++;
		}
		if (lineID.isPresent())
		{
			preparedStatement.setInt(idx, lineID.get().getValue());
		}
	}

	public final List<Delay> getDelays() throws IOException
	{
		final String sql = getSQL("(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay");
		return readFromDatabase(sql, this::setValues);
	}

	protected String getSQL(String what)
	{
		final StringBuilder stringBuilder = new StringBuilder().append("SELECT ").append(what)
				.append(" FROM Stop WHERE stationID = ?");

		weekday.ifPresent(w -> stringBuilder.append(" AND DAYOFWEEK(timetabledTime) = ?"));
		hour.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) = ?"));
		lineID.ifPresent(h -> stringBuilder.append(" AND lineID = ?"));

		return stringBuilder.toString();
	}

	@Override
	protected Optional<Delay> getValue(ResultSet result) throws SQLException
	{
		return Optional.of(new Delay(result.getDouble("delay")));
	}
}
