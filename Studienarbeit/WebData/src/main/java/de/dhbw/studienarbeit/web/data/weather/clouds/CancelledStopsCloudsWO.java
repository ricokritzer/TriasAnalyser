package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.CancelledStopsClouds;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.CancelledStopsCloudsDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.Clouds;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CancelledStopsCloudsWO extends Updateable
{
	private CancelledStopsClouds updater = new CancelledStopsCloudsDB();

	private List<CancelledStopsData<Clouds>> data = new ArrayList<>();

	public CancelledStopsCloudsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<CancelledStopsData<Clouds>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = updater.getCancelledStops();
	}

	public void setUpdater(CancelledStopsClouds updater)
	{
		this.updater = updater;
		update();
	}
}
