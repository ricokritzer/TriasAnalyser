package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayTemperatureCorrelation;
import de.dhbw.studienarbeit.data.reader.database.DelayTempCorrelationDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayTemperatureCorrelationCoefficientWO extends Updateable
{
	private DelayTemperatureCorrelation data = new DelayTemperatureCorrelation(0.0);

	public DelayTemperatureCorrelationCoefficientWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public DelayTemperatureCorrelation getCorrelation()
	{
		return data;
	}

	@Deprecated
	public double getData()
	{
		return data.getDelayCloudsCorrelation();
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayTempCorrelationDB.getDelayTemperatureCorrelation();
	}
}
