package de.dhbw.studienarbeit.data.reader.database;

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
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class DelayRequestDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayPressureDB.class.getName());

	private final StationName stationName;

	private Optional<Weekday> weekday = Optional.empty();
	private Optional<Integer> hour = Optional.empty();
	private Optional<Integer> lineID = Optional.empty();

	public DelayRequestDB(StationName stationName)
	{
		this.stationName = stationName;
	}

	public void setWeekday(Weekday weekday)
	{
		this.weekday = Optional.ofNullable(weekday);
	}

	public void setWeekday(Optional<Weekday> weekday)
	{
		this.weekday = weekday;
	}

	public void setHour(Optional<Integer> hour)
	{
		this.hour = hour;
	}

	public void setHour(int hour)
	{
		this.hour = Optional.ofNullable(Integer.valueOf(hour));
	}

	public void setLineID(Optional<Integer> lineID)
	{
		this.lineID = lineID;
	}

	public void setLineID(int lineID)
	{
		this.lineID = Optional.ofNullable(Integer.valueOf(lineID));
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

	public final List<Delay> getDelays() throws IOException
	{
		final String sql = getSQL();

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			int idx = 1;
			preparedStatement.setString(idx++, stationName.getStationName());

			if (weekday.isPresent())
			{
				preparedStatement.setInt(idx, weekday.get().getIdx());
				idx++;
			}
			if (hour.isPresent())
			{
				preparedStatement.setInt(idx, hour.get());
				idx++;
			}
			if (lineID.isPresent())
			{
				preparedStatement.setInt(idx, lineID.get());
				idx++;
			}

			final List<Delay> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	// public List<LineName> getPossiblleLineNames()
	// {
	//
	// }

	protected String getSQL()
	{
		final StringBuilder stringBuilder = new StringBuilder()
				.append("SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay ")
				.append("FROM Stop, Station WHERE Station.stationID = Stop.stationID ").append("AND Station.name = ?");

		weekday.ifPresent(w -> stringBuilder.append(" AND DAYOFWEEK(timetabledTime) = ?"));
		hour.ifPresent(h -> stringBuilder.append(" AND HOUR(timetabledTime) = ?"));
		lineID.ifPresent(h -> stringBuilder.append(" AND lineID = ?"));

		return stringBuilder.toString();
	}

	public static void main(String[] args) throws IOException
	{
		final DelayRequestDB requestDB = new DelayRequestDB(new StationName("Wörth Alte Bahnmeisterei"));
		requestDB.getDelays().forEach(r -> System.out.println(r.getValue()));
		System.out.println("fertig.");
	}
}
