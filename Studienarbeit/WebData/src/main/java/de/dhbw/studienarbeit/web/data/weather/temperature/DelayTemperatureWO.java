package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperature;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayTemperatureWO extends Updateable
{
	private DelayTemperature delayTemperature = new DelayTemperatureDB();

	private List<DelayTemperatureData> data = new ArrayList<>();

	public DelayTemperatureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayTemperatureData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayTemperature.getDelays();
	}

	public void setDelayTemperature(DelayTemperature delayTemperature)
	{
		this.delayTemperature = delayTemperature;
		update();
	}
}
