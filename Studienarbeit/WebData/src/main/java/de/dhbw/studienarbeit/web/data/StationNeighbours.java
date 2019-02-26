package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;
import de.dhbw.studienarbeit.data.reader.database.TrackDB;

public class StationNeighbours extends Updateable
{
	private List<StationNeighbourDB> data = new ArrayList<>();

	public StationNeighbours()
	{
		DataUpdater.scheduleUpdate(this, 1, DataUpdater.DAYS);
	}

	public List<StationNeighbourDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = new ArrayList<>();
		final List<TrackDB> tracks = TrackDB.getTracks();
		tracks.forEach(track -> StationNeighbourDB.convertToStationNeighbour(track).ifPresent(data::add));
	}
}
