package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountDB;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayRequestDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayRequestDB.class.getName());

	protected final StationID stationID;

	private Optional<Weekday> weekday = Optional.empty();
	private Optional<Hour> hour = Optional.empty();
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

	public void setHour(Optional<Hour> hour)
	{
		this.hour = hour;
	}

	public void setHour(Hour hour)
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

	private static final Optional<Delay> getDelay(ResultSet result)
	{
		try
		{
			final double delay = result.getDouble("delay");
			return Optional.of(new Delay(delay));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + Delay.class.getName(), e);
			return Optional.empty();
		}
	}

	public final Count getCancelledStops() throws IOException
	{
		final String sql = getSQL("count(*) AS total") + " AND realTime IS NULL;";

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			setValues(preparedStatement);

			final List<Count> count = new ArrayList<>();
			database.select(result -> CountDB.getCount(result).ifPresent(count::add), preparedStatement);

			if (count.isEmpty())
			{
				throw new SQLException("Unable to count: " + sql);
			}

			Count c = count.get(0);
			LOGGER.log(Level.FINEST, c + " entries count: " + sql);
			return c;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
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

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			setValues(preparedStatement);

			final List<Delay> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
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
}
