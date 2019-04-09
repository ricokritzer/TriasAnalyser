package de.dhbw.studienarbeit.web.data.weather.pressure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressure;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.Pressure;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayPressureWO extends Updateable
{
	private DelayPressure delayPressure = new DelayPressureDB();

	private List<DelayData<Pressure>> data = new ArrayList<>();

	public DelayPressureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<Pressure>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayPressure.getDelays();
	}

	public void setDelayPressure(DelayPressure delayPressure)
	{
		this.delayPressure = delayPressure;
		update();
	}
}
