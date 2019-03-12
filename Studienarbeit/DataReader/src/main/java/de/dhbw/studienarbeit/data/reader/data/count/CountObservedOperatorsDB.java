package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountObservedOperatorsDB implements CountObservedOperators
{
	private static final Logger LOGGER = Logger.getLogger(CountObservedOperatorsDB.class.getName());

	@Override
	public CountData count()
	{
		try
		{
			return new DatabaseReader().count(
					"SELECT count(*) AS total FROM (SELECT DISTINCT operator FROM Station WHERE observe=true) t;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count observed operators.", e);
			return CountData.UNABLE_TO_COUNT;
		}
	}

}
