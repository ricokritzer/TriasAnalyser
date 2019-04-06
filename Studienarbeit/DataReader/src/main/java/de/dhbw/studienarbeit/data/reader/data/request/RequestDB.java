package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.OperatorName;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationData;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.data.reader.database.DB;
import de.dhbw.studienarbeit.data.reader.database.SQLListHelper;

public class RequestDB extends DB<DelayCountData> implements Request
{
	private final StationData station;

	private List<LineName> lineNames = new ArrayList<>();
	private List<LineDestination> lineDestinations = new ArrayList<>();

	private Optional<Date> startDate = Optional.empty();
	private Optional<Date> endDate = Optional.empty();

	private Optional<Hour> hourStart = Optional.empty();
	private Optional<Hour> hourEnd = Optional.empty();

	private List<Weekday> weekdays = new ArrayList<>();

	@Deprecated
	public RequestDB(StationID stationID)
	{
		this.station = new StationData(stationID, new StationName("Name nicht gesetzt"), new Position(0, 0),
				new OperatorName(""));
	}

	public RequestDB(StationData station)
	{
		this.station = station;
	}

	@Override
	public CountData getCancelledStops() throws IOException
	{
		final List<DelayCountData> data = readFromDatabase(getCountCancelledSQL(), this::setValues);

		if (data.isEmpty())
		{
			return CountData.UNABLE_TO_COUNT;
		}

		return data.get(0).getCount();
	}

	@Override
	public List<DelayCountData> getDelays() throws IOException
	{
		return readFromDatabase(getDelaySQL(), this::setValues);
	}

	@Override
	public List<LineName> getPossibleLineNames() throws IOException
	{
		return new LineNamesAtStationDB().getLineNamesAt(station.getStationID(), lineDestinations);
	}

	@Override
	public List<LineDestination> getPossibleLineDestinations() throws IOException
	{
		return new LineDestinationsAtStationDB().getLineDestinationsAt(station.getStationID(), lineNames);
	}

	@Override
	public Request filterLineNames(Collection<LineName> lineNames)
	{
		this.lineNames.clear();
		this.lineNames.addAll(lineNames);
		return this;
	}

	@Override
	public Request filterLineDestination(Collection<LineDestination> lineDestinations)
	{
		this.lineDestinations.clear();
		this.lineDestinations.addAll(lineDestinations);
		return this;
	}

	@Override
	public Request filterWeekdays(Collection<Weekday> weekdays)
	{
		this.weekdays.clear();
		this.weekdays.addAll(weekdays);
		return this;
	}

	@Override
	public Request filterStartDate(Date start) throws InvalidTimeSpanException
	{
		final Optional<Date> setted = Optional.ofNullable(start);

		if (possibleDate(setted, endDate))
		{
			this.startDate = Optional.ofNullable(start);
			return this;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	@Override
	public Request filterEndDate(Date end) throws InvalidTimeSpanException
	{
		final Optional<Date> setted = Optional.ofNullable(end);

		if (possibleDate(startDate, setted))
		{
			this.endDate = setted;
			return this;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	@Override
	public Request filterStartHour(Hour hour) throws InvalidTimeSpanException
	{
		final Optional<Hour> setted = Optional.ofNullable(hour);

		if (possibleHour(setted, hourEnd))
		{
			this.hourStart = setted;
			return this;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	@Override
	public Request filterEndHour(Hour hour) throws InvalidTimeSpanException
	{
		final Optional<Hour> setted = Optional.ofNullable(hour);

		if (possibleHour(hourStart, setted))
		{
			this.hourEnd = setted;
			return this;
		}
		else
		{
			throw new InvalidTimeSpanException();
		}
	}

	protected String getDelaySQL()
	{
		return getSQL(false);
	}

	protected String getCountCancelledSQL()
	{
		return getSQL(true);
	}

	private String getSQL(boolean realtimeNull)
	{
		final StringBuilder stringBuilder = new StringBuilder().append("SELECT ")
				.append("count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay")
				.append(" FROM Stop WHERE stationID = ?");

		startDate.ifPresent(h -> stringBuilder.append(" AND timetabledTime >= ?"));
		endDate.ifPresent(h -> stringBuilder.append(" AND timetabledTime <= ?"));

		stringBuilder.append(SQLListHelper.createSQLFor(" AND name", lineNames));
		stringBuilder.append(SQLListHelper.createSQLFor(" AND destination", lineDestinations));
		stringBuilder.append(SQLListHelper.createSQLFor(" AND WEEKDAY(timetabledTime)", weekdays));

		hourStart.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) >= ?"));
		hourEnd.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) <= ?"));

		return stringBuilder.append(realtimeNull ? " AND realtime IS NULL" : " AND realtime IS NOT NULL")
				.append(" GROUP BY delay;").toString();
	}

	private void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		int idx = 1;
		preparedStatement.setString(idx++, station.getStationID().getValue());

		if (startDate.isPresent())
		{
			preparedStatement.setTimestamp(idx, new Timestamp(startDate.get().getTime()));
			idx++;
		}

		if (endDate.isPresent())
		{
			preparedStatement.setTimestamp(idx, new Timestamp(endDate.get().getTime()));
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
		}
	}

	@Override
	protected Optional<DelayCountData> getValue(ResultSet result) throws SQLException
	{
		final Delay delay = new Delay(result.getDouble("delay"));
		final CountData count = new CountData(result.getLong("total"));

		return Optional.of(new DelayCountData(delay, count));
	}

	private boolean possibleDate(Optional<Date> start, Optional<Date> end)
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

	private boolean possibleHour(Optional<Hour> start, Optional<Hour> end)
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

	private String getString(List<? extends Object> objects)
	{
		if (objects.isEmpty())
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		sb.append(" (" + objects.get(0));

		for (int i = 1; i < objects.size(); i++)
		{
			sb.append(", ");
			sb.append(objects.get(i));
		}

		sb.append(")");
		return sb.toString();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(station.getName());

		sb.append(getString(weekdays));

		return sb.toString();
	}
}
