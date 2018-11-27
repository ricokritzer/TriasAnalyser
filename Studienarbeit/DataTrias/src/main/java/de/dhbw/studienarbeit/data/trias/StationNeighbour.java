package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class StationNeighbour implements Saveable
{
	private String stationId1;
	private String stationId2;

	public StationNeighbour(String stationId1, String stationId2)
	{
		this.stationId1 = stationId1;
		this.stationId2 = stationId2;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO StationNeighbour SELECT * FROM (SELECT ? s1, ? s2) AS tmp WHERE s1 <> s2 AND NOT EXISTS (SELECT stationID1, stationID2 FROM StationNeighbour WHERE (stationID1 = s1 AND stationID2 = s2) OR (stationID1 = s2 AND stationID2 = s1));";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, stationId1);
		preparedStatement.setString(2, stationId2);
	}
}
