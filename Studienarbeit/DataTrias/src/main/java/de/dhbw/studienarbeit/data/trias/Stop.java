package de.dhbw.studienarbeit.data.trias;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import de.dhbw.studienarbeit.data.helper.database.saver.DataSaverModel;

public class Stop implements DataSaverModel
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
		return "'" + stationID + "', " + line.getId() + ", '" + new Timestamp(timeTabledTime.getTime()) + "', '"
				+ new Timestamp(realTime.getTime()) + "'";
	}
}
