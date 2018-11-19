package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class Stop implements Saveable, Comparable<Stop>
{
	private String stationID;
	private Date timeTabledTime;
	private Optional<Date> realTime = Optional.empty();
	private String lineName;
	private String destination;
	private List<Situation> situations;

	public Stop(String stationID, String lineName, String destination, Date timetabled, Optional<Date> realTime,
			Situation... situations)
	{
		this.stationID = stationID;

		final Calendar cal = Calendar.getInstance();
		cal.setTime(timeTabledTime);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		this.timeTabledTime = cal.getTime();

		realTime.ifPresent(real -> {
			cal.setTime(real);
			cal.add(Calendar.HOUR_OF_DAY, 1);
			this.realTime = Optional.ofNullable(cal.getTime());
		});

		this.lineName = lineName;
		this.destination = destination;
		this.situations = Arrays.asList(situations);
	}

	public String getStationID()
	{
		return stationID;
	}

	public Date getTimeTabledTime()
	{
		return timeTabledTime;
	}

	public Optional<Date> getRealTime()
	{
		return realTime;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		if (obj instanceof Stop)
		{
			Stop stop = (Stop) obj;
			if (stop.getStationID().equals(stationID) && stop.lineName.equals(lineName)
					&& stop.destination.equals(destination) && stop.getTimeTabledTime().equals(timeTabledTime))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(stationID, lineName, destination, timeTabledTime);
	}

	@Override
	public String toString()
	{
		return String.join(", ", stationID, lineName, destination, String.valueOf(timeTabledTime),
				String.valueOf(realTime));
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Stop (stationID, lineID, timeTabledTime, realTime)"
				+ " VALUES (?, (SELECT lineID FROM Line WHERE name = ? AND destination = ?), ?, ?);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, stationID);
		preparedStatement.setString(2, lineName);
		preparedStatement.setString(3, destination);

		preparedStatement.setTimestamp(4, new Timestamp(timeTabledTime.getTime()));

		if (realTime.isPresent())
		{
			preparedStatement.setTimestamp(5, new Timestamp(realTime.get().getTime()));
		}
		else
		{
			preparedStatement.setTimestamp(5, null);
		}
	}

	@Override
	public int compareTo(Stop stop)
	{
		if (!stop.getRealTime().isPresent() && !this.getRealTime().isPresent())
		{
			return 0;
		}
		if (!stop.getRealTime().isPresent())
		{
			return -1;
		}
		if (!this.getRealTime().isPresent())
		{
			return 1;
		}
		if (stop.getRealTime().get().equals(new Date(0)) && this.getRealTime().get().equals(new Date(0)))
		{
			return 0;
		}
		if (stop.getRealTime().get().equals(new Date(0)))
		{
			return -1;
		}
		if (this.getRealTime().get().equals(new Date(0)))
		{
			return 1;
		}
		return this.getRealTime().get().compareTo(stop.getRealTime().get());
	}

	public void save()
	{
		DatabaseSaver.saveData(new Line(lineName, destination));
		DatabaseSaver.saveData(this);
		situations.forEach(DatabaseSaver::saveData);
		situations.forEach(situation -> DatabaseSaver.saveData(new StopSituation(situation, this)));
	}

	public String getLineName()
	{
		return lineName;
	}

	public String getDestination()
	{
		return destination;
	}

	public List<Situation> getSituations()
	{
		return situations;
	}
}
