package de.dhbw.studienarbeit.data.reader.data.weather.text;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayWeatherTextData extends DelayData<WeatherText>
{
	public DelayWeatherTextData(DelayMaximum delayMaximum, DelayAverage delayAverage, WeatherText textDE)
	{
		super(delayMaximum, delayAverage, textDE);
	}

	@Override
	public String getValueString()
	{
		return value.toString();
	}
}
