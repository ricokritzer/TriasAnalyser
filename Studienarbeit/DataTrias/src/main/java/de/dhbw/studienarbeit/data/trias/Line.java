package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class Line implements Saveable
{
	private String name;
	private String destination;

	public Line(String publishedLineName, String destinationText)
	{
		this.name = publishedLineName;
		this.destination = destinationText;
	}

	public String getName()
	{
		return name;
	}

	public String getDestination()
	{
		return destination;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Line (name, destination) SELECT * FROM (SELECT ? n, ? d) AS tmp WHERE NOT EXISTS (SELECT name FROM Line WHERE name = n AND destination = d);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, destination);
	}
}
