package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.ApiKey;
import de.dhbw.studienarbeit.data.helper.DataModel;

public class Stop implements DataModel
{
	private String stopPointName = "";
	private String timetabledTime = "";
	private String estimatedTime = "";
	private String publishedLineName = "";
	private String destinationText = "";

	public Stop(String timetabledTime, String estimatedTime, String publishedLineName, String destinationText)
	{
		super();
		this.timetabledTime = timetabledTime;
		this.estimatedTime = estimatedTime;
		this.publishedLineName = publishedLineName.substring(0, publishedLineName.length() - 2);
		this.destinationText = destinationText.substring(0, destinationText.length() - 2);
	}
	
	public Stop(String stopPointName, String timetabledTime, String estimatedTime, String publishedLineName,
			String destinationText)
	{
		super();
		this.stopPointName = stopPointName.substring(0, stopPointName.length() - 2);
		this.timetabledTime = timetabledTime;
		this.estimatedTime = estimatedTime;
		this.publishedLineName = publishedLineName.substring(0, publishedLineName.length() - 2);
		this.destinationText = destinationText.substring(0, destinationText.length() - 2);
	}

	public long getDelay()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return stopPointName + "\t" + publishedLineName + "\tnach " + destinationText + "\tgeplant: " + timetabledTime
				+ "\tvorraussichtlich: " + estimatedTime;
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
