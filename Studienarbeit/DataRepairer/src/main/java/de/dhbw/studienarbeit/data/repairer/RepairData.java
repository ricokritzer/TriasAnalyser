package de.dhbw.studienarbeit.data.repairer;

import de.dhbw.studienarbeit.data.helper.database.saver.DataSaverModel;

public class RepairData implements DataSaverModel
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
