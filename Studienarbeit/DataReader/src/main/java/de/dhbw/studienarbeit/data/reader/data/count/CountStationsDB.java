package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountStationsDB implements CountStations
{
	private static final Logger LOGGER = Logger.getLogger(CountStationsDB.class.getName());

	@Override
	public CountData count()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stations.", e);
			return CountData.UNABLE_TO_COUNT;
		}
	}
}
