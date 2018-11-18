package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class Situation implements Saveable
{
	private String value;

	public Situation(String value)
	{
		this.value = value;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Situation (text) SELECT * FROM (SELECT ? t) AS tmp WHERE NOT EXISTS (SELECT text FROM Situation WHERE text = t);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, value);
	}
}
