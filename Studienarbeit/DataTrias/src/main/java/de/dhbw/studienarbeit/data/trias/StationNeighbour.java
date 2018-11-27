package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class StationNeighbour implements Saveable
{
	
	private String id1;
	private String id2;
	
	public StationNeighbour(String id1, String id2)
	{
		this.id1 = id1;
		this.id2 = id2;
	}

	@Override
	public String getSQLQuerry()
	{
		return "";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		// TODO Auto-generated method stub

	}

}
