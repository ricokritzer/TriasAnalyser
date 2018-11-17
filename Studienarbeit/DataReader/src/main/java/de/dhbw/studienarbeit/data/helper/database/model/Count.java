package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Count
{
	public static final Count UNABLE_TO_COUNT = new Count(-1);

	protected long count;

	public Count(long count)
	{
		this.count = count;
	}

	@Override
	public String toString()
	{
		return Long.toString(count);
	}

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
