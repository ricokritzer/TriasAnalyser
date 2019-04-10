package de.dhbw.studienarbeit.WebView.requests;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;
import de.dhbw.studienarbeit.data.reader.data.request.Request;

public class RequestGridData
{
	private List<DelayCountData> delays;
	
	private Request request;
	private Color color;
	private CountData canceled;
	
	public RequestGridData(Request request, Color color) throws IOException
	{
		this.request = request;
		this.color = color;
		this.delays = request.getDelays();
		this.canceled = request.getCancelledStops();
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public int getCount()
	{
		return delays.size();
	}
	
	public int getCanceledPercentage()
	{
		return Math.round((canceled.getValue() / getCount()) * 100);
	}
	
	public int getOnTimePercentage()
	{
		for (DelayCountData data : delays)
		{
			if (data.getDelayValue() == 0)
			{
				return Math.round((data.getCountValue() / getCount()) * 100);
			}
		}
		return 0;
	}
	
	@Override
	public String toString()
	{
		return request.toString();
	}
}
