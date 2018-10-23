package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.DataModel;

public class Stop implements DataModel
{
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
	
	public long getDelay()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return "Linie " + publishedLineName + "\t" + destinationText + "\tgeplant: " + timetabledTime
				+ "\tvorraussichtlich: " + estimatedTime;
	}

	@Override
	public String getSQLQuerry()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateData(int attempts) throws IOException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date nextUpdate()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
