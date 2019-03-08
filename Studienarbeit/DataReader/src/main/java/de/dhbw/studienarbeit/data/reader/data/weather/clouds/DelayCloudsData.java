package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayCloudsData extends DelayData
{
	private final double clouds;

	public DelayCloudsData(DelayMaximum delayMaximum, DelayAverage delayAverage, double clouds)
	{
		super(delayMaximum, delayAverage);
		this.clouds = clouds;
	}

	public double getClouds()
	{
		return clouds;
	}
}
