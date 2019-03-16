package de.dhbw.studienarbeit.data.reader.data.count;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountLinesDB implements CountLines
{
	@Override
	public CountData count()
	{
		return new DatabaseReader().count("SELECT count(*) AS total FROM Line;");
	}
}
