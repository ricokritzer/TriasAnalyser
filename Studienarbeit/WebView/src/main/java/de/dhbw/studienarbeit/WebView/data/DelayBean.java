package de.dhbw.studienarbeit.WebView.data;

import java.util.Date;

public class DelayBean
{
	private double sum;
	private double max;
	private double avg;
	private Date lastUpdate;
	
	public DelayBean(double sum, double max, double avg, Date lastUpdate)
	{
		this.sum = sum;
		this.max = max;
		this.avg = avg;
		this.lastUpdate = lastUpdate;
	}

	public double getSum()
	{
		return sum;
	}

	public double getMax()
	{
		return max;
	}

	public double getAvg()
	{
		return avg;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
