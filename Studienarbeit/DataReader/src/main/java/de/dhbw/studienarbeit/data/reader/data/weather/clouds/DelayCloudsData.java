package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayCloudsData implements DelayData
{
	private final double delayAverage;
	private final double delayMaximum;
	private final double clouds;

	public DelayCloudsData(double delayAverage, double delayMaximum, double clouds)
	{
		this.delayAverage = delayAverage;
		this.delayMaximum = delayMaximum;
		this.clouds = clouds;
	}

	public double getClouds()
	{
		return clouds;
	}

	@Override
	public double getDelayMaximum()
	{
		return delayMaximum;
	}

	@Override
	public double getDelayAverage()
	{
		return delayAverage;
	}
}
