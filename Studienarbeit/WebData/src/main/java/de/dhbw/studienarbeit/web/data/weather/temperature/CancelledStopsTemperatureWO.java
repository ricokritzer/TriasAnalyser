package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.CancelledStopsTemperature;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.CancelledStopsTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.Temperature;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CancelledStopsTemperatureWO extends Updateable
{
	private CancelledStopsTemperature updater = new CancelledStopsTemperatureDB();

	private List<CancelledStopsData<Temperature>> data = new ArrayList<>();

	public CancelledStopsTemperatureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<CancelledStopsData<Temperature>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = updater.getCancelledStops();
	}

	public void setUpdater(CancelledStopsTemperature updater)
	{
		this.updater = updater;
		update();
	}
}
