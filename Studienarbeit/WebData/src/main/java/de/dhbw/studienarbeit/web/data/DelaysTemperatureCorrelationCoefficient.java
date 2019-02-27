package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DelayTempCorrelation;

public class DelaysTemperatureCorrelationCoefficient extends Updateable
{
	private double data = 0.0;

	public DelaysTemperatureCorrelationCoefficient(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
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
