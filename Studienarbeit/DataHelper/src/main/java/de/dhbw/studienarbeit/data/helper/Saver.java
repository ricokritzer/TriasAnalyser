package de.dhbw.studienarbeit.data.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Saver
{
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

	public void save(final double lon, final double lat, final double temp, double humitidity, double pressure,
			double wind, double clouds)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("ausgabe.txt", true)))
		{
			final Date date = new Date();

			bw.write(getPartialString("datetime", format.format(date)));
			bw.write(getPartialString("lon", lon));
			bw.write(getPartialString("lat", lat));
			bw.write(getPartialString("temp", temp));
			bw.write(getPartialString("humitidity", humitidity));
			bw.write(getPartialString("pressure", pressure));
			bw.write(getPartialString("wind", wind));
			bw.write(getPartialString("clouds", clouds));
			bw.newLine();
		}
		catch (IOException ex)
		{
			logError(ex);
		}
	}

	public void logError(Exception ex)
	{
		System.err.println("error: " + ex.getMessage());
	}

	private String getPartialString(final String text, final String value)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(text);
		sb.append(":\t");
		sb.append(value);
		return sb.toString();
	}

	private String getPartialString(final String text, final double value)
	{
		return getPartialString(text, "" + value);
	}
}
