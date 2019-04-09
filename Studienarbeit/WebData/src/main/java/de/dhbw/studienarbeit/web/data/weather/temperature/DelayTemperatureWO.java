package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperature;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.Temperature;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayTemperatureWO extends Updateable
{
	private DelayTemperature delayTemperature = new DelayTemperatureDB();

	private List<DelayData<Temperature>> data = new ArrayList<>();

	public DelayTemperatureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<Temperature>> getData()
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
