package de.dhbw.studienarbeit.web.data.weather.humidity;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelationDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayHumidityCorrelationCoefficientWO extends Updateable
{
	private DelayHumidityCorrelation data = new DelayHumidityCorrelation(0.0);

	public DelayHumidityCorrelationCoefficientWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public DelayHumidityCorrelation getCorrelation()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayHumidityCorrelationDB.getCorrelationCoefficient();
	}
}
