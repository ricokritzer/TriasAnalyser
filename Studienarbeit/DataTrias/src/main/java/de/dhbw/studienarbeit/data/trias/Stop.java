package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class Stop implements DataModel
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
		String values = ", ";
		return "INSERT INTO Stop (StationID, LineID, TimeTabledTime, RealTime) VALUES" + " (" + values + ")";
	}

	@Override
	public Date nextUpdate()
	{
		return null;
	}

	@Override
	public void updateData(ApiKey apiKey) throws IOException
	{
		// TODO Auto-generated method stub
		
	}
}
