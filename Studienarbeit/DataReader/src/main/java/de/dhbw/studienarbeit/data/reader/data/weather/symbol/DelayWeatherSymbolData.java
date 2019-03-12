package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayWeatherSymbolData extends DelayData<WeatherSymbol>
{
	public DelayWeatherSymbolData(DelayMaximum delayMaximum, DelayAverage delayAverage, WeatherSymbol symbol)
	{
		super(delayMaximum, delayAverage, symbol);
	}
}
