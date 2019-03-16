package de.dhbw.studienarbeit.data.reader.data.count;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountObservedOperatorsDB implements CountObservedOperators
{
	@Override
	public CountData count()
	{
		return new DatabaseReader()
				.count("SELECT count(*) AS total FROM (SELECT DISTINCT operator FROM Station WHERE observe=true) t;");
	}
}
