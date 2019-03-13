package de.dhbw.studienarbeit.data.reader.data.situation;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayAverage;

public class DelaySituationData
{
	private final SituationName situationName;
	private final DelayAverage delayWithSituation;
	private final DelayAverage delayWithoutSituation;

	public DelaySituationData(SituationName situationName, DelayAverage delayWithSituation,
			DelayAverage delayWithoutSituation)
	{
		super();
		this.situationName = situationName;
		this.delayWithSituation = delayWithSituation;
		this.delayWithoutSituation = delayWithoutSituation;
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

	public Delay getDifference()
	{
		return new Delay(Delay.difference(delayWithSituation, delayWithoutSituation));
	}
}
