package de.dhbw.studienarbeit.web.data.station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationDB;
import de.dhbw.studienarbeit.data.reader.data.station.StationData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayStationWO extends Updateable
{
	private Delay<StationData> delayStation = new DelayStationDB();

	private List<DelayData<StationData>> data = new ArrayList<>();

	public DelayStationWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<StationData>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayStation.getDelays();
	}

	public void setDelayStation(Delay<StationData> delayStation)
	{
		this.delayStation = delayStation;
		update();
	}
}
