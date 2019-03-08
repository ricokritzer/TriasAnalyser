package de.dhbw.studienarbeit.data.reader.data.count;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CountDB
{
	private CountDB()
	{}

	public static final Optional<Count> getCount(ResultSet result)
	{
		try
		{
			return Optional.ofNullable(new Count(result.getLong("total")));
		}
		catch (SQLException e)
		{
			return Optional.empty();
		}
	}
}
