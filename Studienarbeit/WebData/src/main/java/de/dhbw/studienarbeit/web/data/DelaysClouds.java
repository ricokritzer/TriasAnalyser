package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.DelayCloudsDB;

public class DelaysClouds extends Updateable
{
	private List<DelayCloudsDB> data = new ArrayList<>();

	public DelaysClouds()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	public List<DelayCloudsDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayCloudsDB.getDelays();
	}
}
