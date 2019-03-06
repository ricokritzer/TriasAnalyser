package de.dhbw.studienarbeit.web.data.line;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.line.DelayLineData;
import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayLineWO extends Updateable
{
	private List<DelayLineData> data = new ArrayList<>();

	public DelayLineWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayLineData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayLineDB.getDelays();
	}
}
