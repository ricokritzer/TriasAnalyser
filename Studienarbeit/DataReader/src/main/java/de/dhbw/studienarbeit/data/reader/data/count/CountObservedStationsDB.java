package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountObservedStationsDB implements CountObservedStations
{
	private static final Logger LOGGER = Logger.getLogger(CountObservedStationsDB.class.getName());

	@Override
	public CountData count()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station WHERE observe = true;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count observed stations.", e);
			return CountData.UNABLE_TO_COUNT;
		}
	}
}
