package de.dhbw.studienarbeit.data.reader.data.situation;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelaySituationDataPart
{
	private final DelayAverage delay;
	private final boolean withSituation;
	private final CountData count;

	public DelaySituationDataPart(DelayAverage delay, boolean withSituation, CountData count)
	{
		super();
		this.delay = delay;
		this.withSituation = withSituation;
		this.count = count;
	}

	public DelayAverage getDelay()
	{
		return delay;
	}

	public boolean isWithSituation()
	{
		return withSituation;
	}

	public CountData getCount()
	{
		return count;
	}
}
