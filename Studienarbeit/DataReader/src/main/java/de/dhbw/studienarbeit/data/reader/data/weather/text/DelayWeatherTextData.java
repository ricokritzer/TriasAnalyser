package de.dhbw.studienarbeit.data.reader.data.weather.text;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.WeatherSymbol;

public class DelayWeatherTextData extends DelayData<String>
{
	private final WeatherSymbol symbol;

	public DelayWeatherTextData(DelayMaximum delayMaximum, DelayAverage delayAverage, String textDE,
			WeatherSymbol symbol)
	{
		super(delayMaximum, delayAverage, textDE);
		this.symbol = symbol;
	}

	@Deprecated
	public String getText()
	{
		return value;
	}

	@Deprecated
	public String getIconURL()
	{
		return symbol.getURLString();
	}
}
