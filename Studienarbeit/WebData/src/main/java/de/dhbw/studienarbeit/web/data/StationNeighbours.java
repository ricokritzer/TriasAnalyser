package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;

public class StationNeighbours extends Updateable
{
	private List<StationNeighbourDB> data = new ArrayList<>();

	public StationNeighbours()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	public List<StationNeighbourDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = StationNeighbourDB.getStationNeighbours();
	}
}
