package de.dhbw.studienarbeit.data.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextSaver
{
	private static final Logger LOGGER = Logger.getLogger(TextSaver.class.getName());
	public final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
	private final File file;

	public TextSaver(String pathname)
	{
		this.file = new File(pathname);
		LOGGER.log(Level.FINEST, this.getClass().getName() + " created with parameter: " + pathname);
	}

	public void save(final double lon, final double lat, final double temp, double humitidity, double pressure,
			double wind, double clouds)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getPartialString("lon", lon));
		sb.append(getPartialString("lat", lat));
		sb.append(getPartialString("temp", temp));
		sb.append(getPartialString("humitidity", humitidity));
		sb.append(getPartialString("pressure", pressure));
		sb.append(getPartialString("wind", wind));
		sb.append(getPartialString("clouds", clouds));

		write(sb.toString());
		LOGGER.log(Level.FINEST, "Weatherdata saved: " + sb.toString());
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

	public void logError(Exception ex)
	{
		write(ex.getMessage());
		LOGGER.log(Level.WARNING, "Error occured " + ex.getMessage(), ex);
	}

	private void write(final String text)
	{
		final Date date = new Date();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true)))
		{
			bw.write(format.format(date));
			bw.write("\t");
			bw.write(text);
			bw.newLine();
			LOGGER.log(Level.FINEST, "Text written: " + text);
		}
		catch (IOException ex)
		{
			LOGGER.log(Level.ALL, ex.getMessage());
		}
	}
}
