package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;

public class DelaysLine extends Updateable
{
	private List<DelayLineDB> data = new ArrayList<>();

	public DelaysLine()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	public List<DelayLineDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayLineDB.getDelays();
	}
}
