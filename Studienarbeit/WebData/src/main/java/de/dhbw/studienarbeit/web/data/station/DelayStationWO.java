package de.dhbw.studienarbeit.web.data.station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayStationData;
import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayStationWO extends Updateable
{
	private List<DelayStationDB> data = new ArrayList<>();

	public DelayStationWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayStationData> getData()
	{
		return new ArrayList<>(data);
	}

	/**
	 * @deprecated use {@link #getData()} instead.
	 */
	@Deprecated
	public List<DelayStationDB> getList()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayStationDB.getDelays();
	}
}
