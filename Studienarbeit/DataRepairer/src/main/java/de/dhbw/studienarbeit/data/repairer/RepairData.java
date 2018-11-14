package de.dhbw.studienarbeit.data.repairer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		// there are no values to have set.
		// TODO Attention: information security event - because SQL in textfile always be executed.
	}
}
