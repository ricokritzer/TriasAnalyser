package de.dhbw.studienarbeit.data.helper.database.model;

public class DelayLineDB
{
	private final double maximum;
	private final double average;
	private final String lineName;
	private final String lineDestination;

	public DelayLineDB(double delayAverage, double delayMaximum, String lineName, String lineDestination)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.lineName = lineName;
		this.lineDestination = lineDestination;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getAverage()
	{
		return average;
	}

	public String getLineName()
	{
		return lineName;
	}
}
