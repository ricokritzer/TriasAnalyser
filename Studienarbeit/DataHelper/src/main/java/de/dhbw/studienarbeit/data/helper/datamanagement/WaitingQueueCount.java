package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class WaitingQueueCount implements Saveable
{
	private String name;
	private int number;

	public WaitingQueueCount(String name, int number)
	{
		super();
		this.number = number;
		this.name = name;
	}

	public int getCount()
	{
		return number;
	}

	public void setCount(int count)
	{
		this.number = count;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Monitor (name, timeStamp, number) VALUES (?, ?, ?);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, name);
		preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
		preparedStatement.setInt(3, number);
	}
}
