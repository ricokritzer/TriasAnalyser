package de.dhbw.studienarbeit.web.data.weather.humidity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayHumidityData;
import de.dhbw.studienarbeit.data.reader.database.DelayHumidityDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayHumidityWO extends Updateable
{
	private List<DelayHumidityData> data = new ArrayList<>();

	public DelayHumidityWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayHumidityData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayHumidityDB.getDelays();
	}
}
