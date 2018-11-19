package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class Situation implements Saveable
{
	private String id;
	private int version;
	private String summary;

	public Situation(String id, int version, String summary)
	{
		this.id = id;
		this.version = version;
		this.summary = summary;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Situation (text) SELECT * FROM (SELECT ? t) AS tmp WHERE NOT EXISTS (SELECT text FROM Situation WHERE text = t);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, id);
		preparedStatement.setInt(2, version);
		preparedStatement.setString(3, summary);
	}
}
