package de.dhbw.studienarbeit.web.data.weather.pressure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayPressureWO extends Updateable
{
	private List<DelayPressureData> data = new ArrayList<>();

	public DelayPressureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayPressureData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayPressureDB.getDelays();
	}
}
