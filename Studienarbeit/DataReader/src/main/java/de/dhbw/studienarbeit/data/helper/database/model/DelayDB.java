package de.dhbw.studienarbeit.data.helper.database.model;

public class DelayDB
{
	private final double maximum;
	private final double average;
	private final double summary;

	public DelayDB(double delaySummary, double delayAverage, double delayMaximum)
	{
		this.summary = delaySummary;
		this.average = delayAverage;
		this.maximum = delayMaximum;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getAverage()
	{
		return average;
	}

	public double getSummary()
	{
		return summary;
	}
}
