package de.dhbw.studienarbeit.data.helper;

import java.text.SimpleDateFormat;

public class Saver
{
	private final TextSaver saverWeather = new TextSaver("ausgabe.txt");

	public void save(final double lon, final double lat, final double temp, double humitidity, double pressure,
			double wind, double clouds)
	{
		saverWeather.save(lon, lat, temp, humitidity, pressure, wind, clouds);
	}

	public void logError(Exception ex)
	{
		saverWeather.logError(ex);
	}
}
