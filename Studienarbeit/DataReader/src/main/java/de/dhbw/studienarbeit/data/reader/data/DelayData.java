package de.dhbw.studienarbeit.data.reader.data;

public abstract class DelayData
{
	private final double delayMaximum;
	private final double delayAverage;

	public DelayData(double delayMaximum, double delayAverage)
	{
		super();
		this.delayMaximum = delayMaximum;
		this.delayAverage = delayAverage;
	}

	public final double getDelayMaximum()
	{
		return delayMaximum;
	}

	public final double getDelayAverage()
	{
		return delayAverage;
	}
}
