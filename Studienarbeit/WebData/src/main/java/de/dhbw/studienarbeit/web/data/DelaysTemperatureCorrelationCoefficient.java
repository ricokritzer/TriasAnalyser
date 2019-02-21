package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DelayTempCorrelation;

public class DelaysTemperatureCorrelationCoefficient implements Updateable
{
	private static final Logger LOGGER = Logger.getLogger(DelaysTemperatureCorrelationCoefficient.class.getName());

	private double data = 0.0;
	private Date lastUpdate = new Date(0);

	public DelaysTemperatureCorrelationCoefficient()
	{
		DataUpdater.scheduleUpdate(this, 1, DataUpdater.DAYS);
	}

	@Override
	public void update()
	{
		try
		{
			data = DelayTempCorrelation.getCorrelationCoefficient();
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

	public double getData()
	{
		return data;
	}
}
