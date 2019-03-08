package de.dhbw.studienarbeit.web.data.weather.wind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWindData;
import de.dhbw.studienarbeit.data.reader.database.DelayWindDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWindWO extends Updateable
{
	private List<DelayWindData> data = new ArrayList<>();

	public DelayWindWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayWindData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayWindDB.getDelays();
	}
}
