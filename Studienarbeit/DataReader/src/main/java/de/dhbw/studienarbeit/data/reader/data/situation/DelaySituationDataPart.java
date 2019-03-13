package de.dhbw.studienarbeit.data.reader.data.situation;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;

public class DelaySituationDataPart
{
	private final DelayAverage delay;
	private final boolean withSituation;

	public DelaySituationDataPart(DelayAverage delay, boolean withSituation)
	{
		super();
		this.delay = delay;
		this.withSituation = withSituation;
	}

	public DelayAverage getDelay()
	{
		return delay;
	}

	public boolean isWithSituation()
	{
		return withSituation;
	}
}
