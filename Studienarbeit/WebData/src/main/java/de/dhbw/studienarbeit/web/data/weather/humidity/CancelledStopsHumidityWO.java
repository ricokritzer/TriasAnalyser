package de.dhbw.studienarbeit.web.data.weather.humidity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.CancelledStopsHumidity;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.CancelledStopsHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.Humidity;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CancelledStopsHumidityWO extends Updateable
{
	private CancelledStopsHumidity updater = new CancelledStopsHumidityDB();

	private List<CancelledStopsData<Humidity>> data = new ArrayList<>();

	public CancelledStopsHumidityWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<CancelledStopsData<Humidity>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = updater.getCancelledStops();
	}

	public void setUpdater(CancelledStopsHumidity updater)
	{
		this.updater = updater;
		update();
	}
}
