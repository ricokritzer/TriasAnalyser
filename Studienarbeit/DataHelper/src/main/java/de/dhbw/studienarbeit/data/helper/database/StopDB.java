package de.dhbw.studienarbeit.data.helper.database;

import java.util.Date;

public class StopDB
{
	private final int stopID;
	private final String stationID;
	private final int lineID;
	private final Date timeTabledTime;
	private final Date realTime;

	public StopDB(int stopID, String stationID, int lineID, Date timeTabledTime, Date realTime)
	{
		super();
		this.stopID = stopID;
		this.stationID = stationID;
		this.lineID = lineID;
		this.timeTabledTime = timeTabledTime;
		this.realTime = realTime;
	}

	public long delay()
	{
		return timeTabledTime.getTime() - realTime.getTime();
	}

	public int getStopID()
	{
		return stopID;
	}

	public String getStationID()
	{
		return stationID;
	}

	public int getLineID()
	{
		return lineID;
	}

	public Date getTimeTabledTime()
	{
		return timeTabledTime;
	}

	public Date getRealTime()
	{
		return realTime;
	}
}
