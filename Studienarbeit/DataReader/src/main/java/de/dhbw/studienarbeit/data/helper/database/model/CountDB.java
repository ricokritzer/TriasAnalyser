package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CountDB
{
	public static final CountDB UNABLE_TO_COUNT = new CountDB(-1);

	private long count;

	public CountDB(long count)
	{
		this.count = count;
	}

	/*
	 * Do not extract the value. For better type safety make your own count object
	 * and override the toString() method.
	 */
	@Deprecated
	public long getValue()
	{
		return count;
	}

	public static final Optional<CountDB> getCount(ResultSet result)
	{
		try
		{
			return Optional.ofNullable(new CountDB(result.getLong("total")));
		}
		catch (SQLException e)
		{
			return Optional.empty();
		}
	}
}
