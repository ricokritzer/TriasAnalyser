package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DelayTempDB;

public class DelaysTemperature implements Updateable
{
	private static final Logger LOGGER = Logger.getLogger(DelaysTemperature.class.getName());

	private List<DelayTempDB> data = new ArrayList<>();
	private Date lastUpdate = new Date(0);

	public DelaysTemperature()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	@Override
	public void update()
	{
		try
		{
			data = DelayTempDB.getDelays();
			lastUpdate = new Date();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update.", e);
		}
	}

	@Override
	public Date lastUpdated()
	{
		return lastUpdate;
	}

	public List<DelayTempDB> getData()
	{
		return data;
	}
}
