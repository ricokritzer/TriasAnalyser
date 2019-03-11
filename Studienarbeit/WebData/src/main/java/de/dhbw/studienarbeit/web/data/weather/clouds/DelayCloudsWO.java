package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayClouds;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudsDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudsData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayCloudsWO extends Updateable
{
	private DelayClouds delayClouds = new DelayCloudsDB();

	private List<DelayCloudsData> data = new ArrayList<>();

	public DelayCloudsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayCloudsData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayClouds.getDelays();
	}

	public void setDelayClouds(DelayClouds delayClouds)
	{
		this.delayClouds = delayClouds;
	}
}
