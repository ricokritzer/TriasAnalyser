package de.dhbw.studienarbeit.data.trias;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class Stop implements Saveable
{
	private String stationID;
	private Line line;
	private Date timeTabledTime;
	private Date realTime;

	public Stop(String stationID, Line line, Date timetabled, Date realTime)
	{
		this.stationID = stationID;
		this.line = line;
		this.timeTabledTime = timetabled;
		this.realTime = realTime;
	}

	public String getStationID()
	{
		return stationID;
	}

	public Line getLine()
	{
		return line;
	}

	public Date getTimeTabledTime()
	{
		return timeTabledTime;
	}

	public Date getRealTime()
	{
		return realTime;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Stop (stationID, lineID, timeTabledTime, realTime) VALUES (" + getValues() + ")";
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
			if (stop.getStationID().equals(stationID) && stop.getLine().equals(line)
					&& stop.getTimeTabledTime().equals(timeTabledTime))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(stationID, line, timeTabledTime);
	}

	public String getValues()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeTabledTime);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		Timestamp timeTabledECT = new Timestamp(cal.getTimeInMillis());
		cal.setTime(realTime);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		Timestamp realTimeECT = new Timestamp(cal.getTimeInMillis());
		return "'" + stationID + "', " + line.getId() + ", '" + timeTabledECT + "', '"
				+ realTimeECT + "'";
	}
	
	@Override
	public String toString()
	{
		return String.join(", ", stationID, String.valueOf(line.getId()), String.valueOf(timeTabledTime), String.valueOf(realTime));
	}
}
