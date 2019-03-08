package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayCloudsData extends DelayData
{
	private final double clouds;

	public DelayCloudsData(double delayAverage, double delayMaximum, double clouds)
	{
		super(delayMaximum, delayAverage);
		this.clouds = clouds;
	}

	public double getClouds()
	{
		return clouds;
	}
}
