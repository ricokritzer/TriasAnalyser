package de.dhbw.studienarbeit.web.data.weather.wind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.CancelledStopsWind;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.CancelledStopsWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.Wind;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CancelledStopsWindWO extends Updateable
{
	private CancelledStopsWind updater = new CancelledStopsWindDB();

	private List<CancelledStopsData<Wind>> data = new ArrayList<>();

	public CancelledStopsWindWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<CancelledStopsData<Wind>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = updater.getCancelledStops();
	}

	public void setUpdater(CancelledStopsWind updater)
	{
		this.updater = updater;
		update();
	}
}
