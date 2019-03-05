package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DelayTempCorrelation;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayTemperatureCorrelationCoefficientWO extends Updateable
{
	private double data = 0.0;

	public DelayTemperatureCorrelationCoefficientWO(Optional<DataUpdater> updater)
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
