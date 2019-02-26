package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;
import de.dhbw.studienarbeit.data.reader.database.TrackDB;

public class StationNeighbours extends Updateable
{
	protected List<StationNeighbourDB> data = new ArrayList<>();

	public StationNeighbours(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DataUpdater.DAYS, this));
	}

	public List<StationNeighbourDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		final List<TrackDB> tracks = TrackDB.getTracks();
		tracks.forEach(track -> StationNeighbourDB.convertToStationNeighbour(track).ifPresent(this::renewData));
	}

	protected void renewData(StationNeighbourDB stationNeighbourDB)
	{
		data.remove(stationNeighbourDB);
		data.add(stationNeighbourDB);
	}
}
