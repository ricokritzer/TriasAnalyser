package de.dhbw.studienarbeit.data.reader.data.count;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CountDB
{
	private CountDB()
	{}

	public static final Optional<CountData> getCount(ResultSet result)
	{
		try
		{
			return Optional.ofNullable(new CountData(result.getLong("total")));
		}
		catch (SQLException e)
		{
			return Optional.empty();
		}
	}
}
