package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;

public class DelaysStation extends Updateable
{
	private List<DelayStationDB> data = new ArrayList<>();

	public DelaysStation()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	public List<DelayStationDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayStationDB.getDelays();
	}
}
