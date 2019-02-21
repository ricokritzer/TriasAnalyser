package de.dhbw.studienarbeit.web.data;

import java.io.IOException;

import de.dhbw.studienarbeit.data.reader.database.DelayTempCorrelation;

public class DelaysTemperatureCorrelationCoefficient extends Updateable
{
	private double data = 0.0;

	public DelaysTemperatureCorrelationCoefficient()
	{
		DataUpdater.scheduleUpdate(this, 1, DataUpdater.DAYS);
	}

	public double getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayTempCorrelation.getCorrelationCoefficient();
	}
}
