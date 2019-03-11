package de.dhbw.studienarbeit.web.data.station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbour;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbourDB;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbourData;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbour;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourDB;
import de.dhbw.studienarbeit.data.reader.data.station.StationNeighbourData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayStationNeighbourWO extends Updateable
{
	private StationNeighbour stationNeighbour = new StationNeighbourDB();
	private DelayStationNeighbour delayStationNeighbour = new DelayStationNeighbourDB();

	protected List<DelayStationNeighbourData> data = new ArrayList<>();

	public DelayStationNeighbourWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public List<DelayStationNeighbourData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		final List<StationNeighbourData> tracks = stationNeighbour.getStationNeighbours();
		tracks.forEach(track -> delayStationNeighbour.convertToStationNeighbour(track).ifPresent(this::renewData));
	}

	protected void renewData(DelayStationNeighbourData stationNeighbour)
	{
		data.remove(stationNeighbour);
		data.add(stationNeighbour);
	}

	public void setStationNeighbour(StationNeighbour stationNeighbour, DelayStationNeighbour delayStationNeighbour)
	{
		this.stationNeighbour = stationNeighbour;
		this.delayStationNeighbour = delayStationNeighbour;
		data = new ArrayList<>();
		update();
	}
}
