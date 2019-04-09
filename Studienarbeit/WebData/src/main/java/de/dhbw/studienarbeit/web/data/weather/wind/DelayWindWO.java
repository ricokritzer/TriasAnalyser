package de.dhbw.studienarbeit.web.data.weather.wind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWind;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.Wind;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWindWO extends Updateable
{
	private DelayWind delayWind = new DelayWindDB();

	private List<DelayData<Wind>> data = new ArrayList<>();

	public DelayWindWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<Wind>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWind.getDelays();
	}

	public void setDelayWind(DelayWind delayWind)
	{
		this.delayWind = delayWind;
		update();
	}
}
