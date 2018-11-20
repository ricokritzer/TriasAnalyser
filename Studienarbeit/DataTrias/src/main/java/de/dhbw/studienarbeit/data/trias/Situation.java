package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

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
		return "INSERT INTO Situation (situationID, version, text) "
				+ "SELECT * FROM (SELECT ? s, ? v, ? t) AS tmp WHERE NOT EXISTS "
				+ "(SELECT * FROM Situation WHERE situationID = s AND version = v AND text = t);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, id);
		preparedStatement.setInt(2, version);
		preparedStatement.setString(3, summary);
	}

	public String getId()
	{
		return id;
	}

	public int getVersion()
	{
		return version;
	}

	public String getSummary()
	{
		return summary;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Situation))
		{
			return false;
		}
		Situation other = (Situation) obj;
		return this.id.equals(other.getId()) && this.version == other.getVersion() && this.summary.equals(other.summary);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(id, version, summary);
	}
}
