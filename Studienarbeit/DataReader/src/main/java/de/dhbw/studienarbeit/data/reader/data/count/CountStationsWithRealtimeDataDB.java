package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountStationsWithRealtimeDataDB implements CountStationsWithRealtimeData
{
	private static final Logger LOGGER = Logger.getLogger(CountStationsWithRealtimeDataDB.class.getName());

	@Override
	public CountData count()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station WHERE stopSaved = true;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stations with realtime data.", e);
			return CountData.UNABLE_TO_COUNT;
		}
	}
}
