package de.dhbw.studienarbeit.data.stationNeighbourLinker;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class StationNeighbour implements Saveable
{
	private String stationId1;
	private String stationId2;
	private int lineID;

	public StationNeighbour(String stationId1, String stationId2, int lineId)
	{
		this.stationId1 = stationId1;
		this.stationId2 = stationId2;
		this.lineID = lineId;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO StationNeighbour (stationID1, stationID2, lineID) SELECT * FROM (SELECT ? s1, ? s2, ? l) AS tmp WHERE s1 <> s2 AND NOT EXISTS (SELECT stationID1, stationID2, lineID FROM StationNeighbour WHERE stationID1 = s1 AND stationID2 = s2 AND l = lineID);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, stationId1);
		preparedStatement.setString(2, stationId2);
		preparedStatement.setInt(3, lineID);
	}
}
