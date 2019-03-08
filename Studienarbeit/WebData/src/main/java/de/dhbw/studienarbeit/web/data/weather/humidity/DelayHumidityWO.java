package de.dhbw.studienarbeit.web.data.weather.humidity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidity;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayHumidityWO extends Updateable
{
	private DelayHumidity delayHumidity = new DelayHumidityDB();

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
		data = delayHumidity.getDelays();
	}
}
