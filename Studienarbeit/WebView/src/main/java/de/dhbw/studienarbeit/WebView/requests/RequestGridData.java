package de.dhbw.studienarbeit.WebView.requests;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;
import de.dhbw.studienarbeit.data.reader.data.request.Request;

public class RequestGridData
{
	private List<DelayCountData> delays;
	
	private Request request;
	private int number;
	private CountData canceled;
	
	public RequestGridData(Request request, int number) throws IOException
	{
		this.request = request;
		this.number = number + 1;
		this.delays = request.getDelays();
		this.canceled = Optional.ofNullable(request.getCancelledStops()).orElse(new CountData(0));
	}
	
	public int getLowestDelay()
	{
		return (int) delays.get(0).getDelayInMinutes();
	}
	
	public int getHighestDelay()
	{
		return (int) delays.get(delays.size() - 1).getDelayInMinutes();
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public int getCount()
	{
		int i = 0;
		for (DelayCountData delay : delays)
		{
			i += delay.getCountValue();
		}
		return i;
	}
	
	public double getCanceledPercentage()
	{
		long value = Math.round((Double.valueOf(canceled.getValue()) / Double.valueOf(getCount())) * 10000d);
		return value / 100d;
	}
	
	public double getOnTimePercentage()
	{
		for (DelayCountData data : delays)
		{
			if (data.getDelayValue() == 0)
			{
				long value = Math.round((Double.valueOf(data.getCountValue()) / Double.valueOf(getCount())) * 10000d);
				return value / 100d;
			}
		}
		return 0;
	}
	
	@Override
	public String toString()
	{
		return request.toString();
	}

	public long getHighestCount()
	{
		long highest = 0;
		for (DelayCountData data : delays)
		{
			if (data.getCountValue() > highest)
			{
				highest = data.getCountValue();
			}
		}
		return highest;
	}

	public int getAddedCountAt(int delay)
	{
		int added = 0;
		while (delay <= getHighestDelay())
		{
			added += getCountAt(delay);
			delay++;
		}
		return added;
	}

	private long getCountAt(int delay)
	{
		for (DelayCountData data : delays)
		{
			if (data.getDelayInMinutes() == delay)
			{
				return data.getCountValue();
			}
		}
		return 0;
	}
}
