package de.dhbw.studienarbeit.data.helper.database;

public class ValueNotNull implements SqlCondition
{
	private final String sql;

	public ValueNotNull(final String name)
	{
		sql = new StringBuilder().append(name).append(" IS NOT NULL").toString();
	}

	@Override
	public String toString()
	{
		return sql;
	}
}
