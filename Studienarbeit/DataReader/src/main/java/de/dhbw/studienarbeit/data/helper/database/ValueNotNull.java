package de.dhbw.studienarbeit.data.helper.database;

public class ValueNotNull implements SqlCondition
{
	private final String sql;

	public ValueNotNull(final String name)
	{
		sql = new StringBuilder(name).append(" IS NOT NULL").toString();
	}

	@Override
	public String toString()
	{
		return sql;
	}
}
