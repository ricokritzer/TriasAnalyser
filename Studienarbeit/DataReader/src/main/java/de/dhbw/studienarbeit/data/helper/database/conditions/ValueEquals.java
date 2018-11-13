package de.dhbw.studienarbeit.data.helper.database.conditions;

public class ValueEquals implements Condition
{
	private final String sql;

	public ValueEquals(final String name, final String value)
	{
		sql = new StringBuilder().append(name).append(" = '").append(value).append("'").toString();
	}

	public ValueEquals(final String name, final double value)
	{
		sql = new StringBuilder().append(name).append(" = ").append(value).toString();
	}

	public ValueEquals(final String name, final int value)
	{
		sql = new StringBuilder().append(name).append(" = ").append(value).toString();
	}

	public ValueEquals(final String name, final boolean value)
	{
		sql = new StringBuilder().append(name).append(" = ").append(value).toString();
	}

	@Override
	public String getSqlStatement()
	{
		return sql;
	}
}
