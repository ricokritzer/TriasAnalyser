package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountLinesDB implements CountLines
{
	private static final Logger LOGGER = Logger.getLogger(CountLinesDB.class.getName());

	@Override
	public CountData count()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Line;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count lines.", e);
			return CountData.UNABLE_TO_COUNT;
		}
	}
}
