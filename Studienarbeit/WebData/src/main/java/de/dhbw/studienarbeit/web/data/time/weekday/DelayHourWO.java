package de.dhbw.studienarbeit.web.data.time.weekday;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.time.DelayHourData;
import de.dhbw.studienarbeit.data.reader.database.DelayHourDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayHourWO extends Updateable
{
	private List<DelayHourData> data = new ArrayList<>();

	public DelayHourWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayHourData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayHourDB.getDelays();
	}
}
