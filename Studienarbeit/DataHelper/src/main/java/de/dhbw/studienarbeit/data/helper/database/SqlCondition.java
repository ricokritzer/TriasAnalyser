package de.dhbw.studienarbeit.data.helper.database;

public class SqlCondition
{
	private final String sql;

	public SqlCondition(final String name, final String value)
	{
		sql = new StringBuilder().append(name).append(" = '").append(value).append("'").toString();
	}

	public SqlCondition(final String name, final double value)
	{
		sql = new StringBuilder().append(name).append(" = ").append(value).toString();
	}

	public SqlCondition(final String name, final int value)
	{
		sql = new StringBuilder().append(name).append(" = ").append(value).toString();
	}

	@Override
	public String toString()
	{
		return sql;
	}
}
