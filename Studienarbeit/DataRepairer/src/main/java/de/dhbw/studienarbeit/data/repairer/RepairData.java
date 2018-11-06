package de.dhbw.studienarbeit.data.repairer;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class RepairData implements Saveable
{
	private final String sql;

	public RepairData(String sql)
	{
		this.sql = sql;
	}

	@Override
	public String getSQLQuerry()
	{
		return sql;
	}
}
