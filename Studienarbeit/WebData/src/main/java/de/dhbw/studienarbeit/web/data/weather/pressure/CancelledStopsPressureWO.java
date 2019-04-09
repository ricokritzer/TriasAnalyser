package de.dhbw.studienarbeit.web.data.weather.pressure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.CancelledStopsPressure;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.CancelledStopsPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.Pressure;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CancelledStopsPressureWO extends Updateable
{
	private CancelledStopsPressure updater = new CancelledStopsPressureDB();

	private List<CancelledStopsData<Pressure>> data = new ArrayList<>();

	public CancelledStopsPressureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<CancelledStopsData<Pressure>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = updater.getCancelledStops();
	}

	public void setUpdater(CancelledStopsPressure updater)
	{
		this.updater = updater;
		update();
	}
}
