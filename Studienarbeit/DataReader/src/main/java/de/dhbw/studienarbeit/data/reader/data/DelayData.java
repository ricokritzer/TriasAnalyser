package de.dhbw.studienarbeit.data.reader.data;

public abstract class DelayData
{
	private final Delay delayMaximum;
	private final Delay delayAverage;

	public DelayData(double delayMaximum, double delayAverage)
	{
		super();
		this.delayMaximum = new Delay(delayMaximum);
		this.delayAverage = new Delay(delayAverage);
	}

	public final Delay getMaximum()
	{
		return delayMaximum;
	}

	public final Delay getAverage()
	{
		return delayAverage;
	}

	@Deprecated
	public final double getDelayMaximum()
	{
		return delayMaximum.getValue();
	}

	@Deprecated
	public final double getDelayAverage()
	{
		return delayAverage.getValue();
	}
}
