package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountOperatorsDB implements CountOperators
{
	private static final Logger LOGGER = Logger.getLogger(CountOperators.class.getName());

	@Override
	public CountData countOperators()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Operator;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count operators.", e);
			return CountData.UNABLE_TO_COUNT;
		}
	}

	@Override
	public CountData countObservedOperators()
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
