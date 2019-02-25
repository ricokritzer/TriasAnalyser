package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.DelayWeatherTextDB;

public class DelaysWeatherText extends Updateable
{
	private List<DelayWeatherTextDB> data = new ArrayList<>();

	public DelaysWeatherText()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	public List<DelayWeatherTextDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayWeatherTextDB.getDelays();
	}
}
