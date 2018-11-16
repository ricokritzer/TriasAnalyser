package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class Stop2 implements Saveable, Comparable<Stop2>
{
	private String stationID;
	private Date timeTabledTime;
	private Optional<Date> realTime = Optional.empty();
	private String lineName;
	private String destination;

	public Stop2(String stationID, String lineName, String destination, Date timetabled, Optional<Date> realTime)
	{
		this.stationID = stationID;
		this.timeTabledTime = timetabled;
		this.realTime = realTime;
		this.lineName = lineName;
		this.destination = destination;
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
		if (obj instanceof Stop2)
		{
			Stop2 stop = (Stop2) obj;
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

		Calendar cal = Calendar.getInstance();
		cal.setTime(timeTabledTime);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		preparedStatement.setTimestamp(4, new Timestamp(cal.getTimeInMillis()));

		if (realTime.isPresent())
		{
			cal.setTime(realTime.get());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			preparedStatement.setTimestamp(5, new Timestamp(cal.getTimeInMillis()));
		}
		else
		{
			preparedStatement.setTimestamp(5, null);
		}
	}

	@Override
	public int compareTo(Stop2 stop)
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

	public void save() throws IOException
	{
		DatabaseSaver saver = new DatabaseSaver();
		saver.save(new Line(lineName, destination));
		saver.save(this);
	}
}
