package de.dhbw.studienarbeit.data.reader.data;

public abstract class DelayData<T>
{
	private final DelayMaximum delayMaximum;
	private final DelayAverage delayAverage;
	protected final T value;

	public DelayData(DelayMaximum delayMaximum, DelayAverage delayAverage, T value)
	{
		super();
		this.value = value;
		this.delayMaximum = delayMaximum;
		this.delayAverage = delayAverage;
	}

	public final DelayMaximum getMaximum()
	{
		return delayMaximum;
	}

	public final DelayAverage getAverage()
	{
		return delayAverage;
	}

	public final T getValue()
	{
		return value;
	}
}
