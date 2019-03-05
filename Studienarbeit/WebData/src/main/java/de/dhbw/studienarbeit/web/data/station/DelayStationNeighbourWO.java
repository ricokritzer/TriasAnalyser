package de.dhbw.studienarbeit.web.data.station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayStationNeighbourData;
import de.dhbw.studienarbeit.data.reader.database.DelayStationNeighbourDB;
import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayStationNeighbourWO extends Updateable
{
	protected List<DelayStationNeighbourDB> data = new ArrayList<>();

	public DelayStationNeighbourWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public List<DelayStationNeighbourData> getData()
	{
		return new ArrayList<>(data);
	}

	/**
	 * @deprecated use {@link #getData()} instead.
	 */
	@Deprecated
	public List<DelayStationNeighbourDB> getList()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		final List<StationNeighbourDB> tracks = StationNeighbourDB.getTracks();
		tracks.forEach(track -> DelayStationNeighbourDB.convertToStationNeighbour(track).ifPresent(this::renewData));
	}

	protected void renewData(DelayStationNeighbourDB stationNeighbourDB)
	{
		data.remove(stationNeighbourDB);
		data.add(stationNeighbourDB);
	}
}
