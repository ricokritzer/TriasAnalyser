package de.dhbw.studienarbeit.data.reader.data.situation;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelaySituationData
{
	private final SituationName situationName;
	private final DelayAverage delayWithSituation;
	private final DelayAverage delayWithoutSituation;
	private final CountData countWithSituation;
	private final CountData countWithoutSituation;

	public DelaySituationData(SituationName situationName, DelayAverage delayWithSituation,
			DelayAverage delayWithoutSituation, CountData countWithSituation, CountData countWithoutSituation)
	{
		super();
		this.situationName = situationName;
		this.delayWithSituation = delayWithSituation;
		this.delayWithoutSituation = delayWithoutSituation;
		this.countWithSituation = countWithSituation;
		this.countWithoutSituation = countWithoutSituation;
	}

	public SituationName getSituationName()
	{
		return situationName;
	}

	public DelayAverage getDelayWithSituation()
	{
		return delayWithSituation;
	}

	public DelayAverage getDelayWithoutSituation()
	{
		return delayWithoutSituation;
	}

	public CountData getCountWithSituation()
	{
		return countWithSituation;
	}

	public CountData getCountWithoutSituation()
	{
		return countWithoutSituation;
	}

	public Delay getDifference()
	{
		return new Delay(Delay.difference(delayWithSituation, delayWithoutSituation));
	}
}
