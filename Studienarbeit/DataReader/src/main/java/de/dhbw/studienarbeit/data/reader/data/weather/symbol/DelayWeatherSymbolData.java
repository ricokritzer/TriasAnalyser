package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayWeatherSymbolData extends DelayData
{
	private static final String URL_PRE = "http://openweathermap.org/img/w/";
	private static final String URL_END = ".png";

	private final String icon;

	public DelayWeatherSymbolData(DelayMaximum delayMaximum, DelayAverage delayAverage, String icon)
	{
		super(delayMaximum, delayAverage);
		this.icon = icon;
	}

	public String getIconURL()
	{
		return new StringBuilder(URL_PRE).append(icon).append(URL_END).toString();
	}
}
