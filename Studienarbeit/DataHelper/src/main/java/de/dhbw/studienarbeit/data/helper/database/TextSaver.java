package de.dhbw.studienarbeit.data.helper.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextSaver implements Saver
{
	private static final Logger LOGGER = Logger.getLogger(TextSaver.class.getName());
	public final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
	private final File file;

	public TextSaver(String pathname)
	{
		this.file = new File(pathname);
		LOGGER.log(Level.FINEST, this.getClass().getName() + " created with parameter: " + pathname);
	}

	@Override
	public void save(DataSaverModel model)
	{
		write(model.getSQLQuerry());
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
