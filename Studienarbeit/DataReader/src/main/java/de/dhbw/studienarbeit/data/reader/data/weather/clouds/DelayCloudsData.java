package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayCloudsData extends DelayData<Clouds>
{
	public DelayCloudsData(DelayMaximum delayMaximum, DelayAverage delayAverage, Clouds clouds)
	{
		super(delayMaximum, delayAverage, clouds);
	}

	@Override
	public String getValueString()
	{
		return value.toString();
	}
}
