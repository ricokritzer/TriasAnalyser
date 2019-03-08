package de.dhbw.studienarbeit.data.reader.data;

public abstract class DelayData
{
	private final DelayMaximum delayMaximum;
	private final DelayAverage delayAverage;

	public DelayData(double delayMaximum, double delayAverage)
	{
		super();
		this.delayMaximum = new DelayMaximum(delayMaximum);
		this.delayAverage = new DelayAverage(delayAverage);
	}

	public final DelayMaximum getMaximum()
	{
		return delayMaximum;
	}

	public final DelayAverage getAverage()
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
