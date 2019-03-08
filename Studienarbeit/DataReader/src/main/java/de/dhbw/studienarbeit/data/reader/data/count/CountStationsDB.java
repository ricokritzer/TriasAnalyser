package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountStationsDB implements CountStations
{
	private static final Logger LOGGER = Logger.getLogger(CountStationsDB.class.getName());

	@Override
	public Count countStations()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stations.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}

	@Override
	public Count countStationsWithRealtimeData()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station WHERE stopSaved = true;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stations with realtime data.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}

	@Override
	public Count countObservedStations()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station WHERE observe = true;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count observed stations.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}
}
