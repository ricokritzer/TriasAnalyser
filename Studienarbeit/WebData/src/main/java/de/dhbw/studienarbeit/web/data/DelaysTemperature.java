package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.DelayTempDB;

public class DelaysTemperature extends Updateable
{
	private List<DelayTempDB> data = new ArrayList<>();

	public DelaysTemperature()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	public List<DelayTempDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayTempDB.getDelays();
	}
}
