package de.dhbw.studienarbeit.data.reader.data.weather.text;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayWeatherTextData extends DelayData
{
	private static final String URL_PRE = "http://openweathermap.org/img/w/";
	private static final String URL_END = ".png";

	private final String textDE;
	private final String icon;

	public DelayWeatherTextData(DelayMaximum delayMaximum, DelayAverage delayAverage, String textDE, String icon)
	{
		super(delayMaximum, delayAverage);
		this.textDE = textDE;
		this.icon = icon;
	}

	public String getText()
	{
		return textDE;
	}

	public String getIcon()
	{
		return icon;
	}

	public String getIconURL()
	{
		return new StringBuilder(URL_PRE).append(icon).append(URL_END).toString();
	}
}
