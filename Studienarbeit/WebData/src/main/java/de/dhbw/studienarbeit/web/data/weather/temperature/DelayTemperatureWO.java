package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayTemperatureData;
import de.dhbw.studienarbeit.data.reader.database.DelayTempDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayTemperatureWO extends Updateable
{
	private List<DelayTempDB> data = new ArrayList<>();

	public DelayTemperatureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayTemperatureData> getData()
	{
		return new ArrayList<>(data);
	}

	/**
	 * @deprecated use {@link #getData()} instead.
	 */
	@Deprecated
	public List<DelayTempDB> getList()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayTempDB.getDelays();
	}
}
