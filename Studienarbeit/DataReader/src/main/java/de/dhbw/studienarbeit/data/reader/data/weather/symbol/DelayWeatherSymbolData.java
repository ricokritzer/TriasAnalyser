package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayWeatherSymbolData extends DelayData<WeatherSymbol>
{
	public DelayWeatherSymbolData(DelayMaximum delayMaximum, DelayAverage delayAverage, CountData count,
			WeatherSymbol symbol)
	{
		super(delayMaximum, delayAverage, count, symbol);
	}

	@Override
	public String getValueString()
	{
		return value.getName();
	}
}
