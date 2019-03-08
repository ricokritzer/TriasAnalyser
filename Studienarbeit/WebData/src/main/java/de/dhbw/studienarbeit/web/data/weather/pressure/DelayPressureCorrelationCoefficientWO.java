package de.dhbw.studienarbeit.web.data.weather.pressure;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelationData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayPressureCorrelationCoefficientWO extends Updateable
{
	private DelayPressureCorrelation correlation = new DelayPressureCorrelationDB();

	private DelayPressureCorrelationData data = new DelayPressureCorrelationData(0.0);

	public DelayPressureCorrelationCoefficientWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public DelayPressureCorrelationData getCorrelation()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = correlation.getDelayPressureCorrelation();
	}
}
