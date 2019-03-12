package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherSymbol
{
	private static final String URL_PRE = "http://openweathermap.org/img/w/";
	private static final String URL_END = ".png";

	private final String name;

	public WeatherSymbol(String name)
	{
		this.name = name;
	}

	public String getURLString()
	{
		return new StringBuilder(URL_PRE).append(name).append(URL_END).toString();
	}

	public URL getURL() throws MalformedURLException
	{
		return new URL(getURLString());
	}
}
