package de.dhbw.studienarbeit.data.helper.database.conditions;

public class ValueNotNull implements Condition
{
	private final String sql;

	public ValueNotNull(final String name)
	{
		sql = new StringBuilder(name).append(" IS NOT NULL").toString();
	}

	@Override
	public String getSqlStatement()
	{
		return sql;
	}
}
