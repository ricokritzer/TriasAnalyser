package de.dhbw.studienarbeit.data.reader.data.count;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountStopsDB implements CountStops
{
	@Override
	public CountData count()
	{
		return new DatabaseReader().count("SELECT count(*) AS total FROM Stop;");
	}
}
