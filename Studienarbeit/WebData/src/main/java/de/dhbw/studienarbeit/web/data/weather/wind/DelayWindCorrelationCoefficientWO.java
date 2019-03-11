package de.dhbw.studienarbeit.web.data.weather.wind;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelationData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWindCorrelationCoefficientWO extends Updateable
{
	private DelayWindCorrelation delayWindCorrelation = new DelayWindCorrelationDB();

	private DelayWindCorrelationData data = new DelayWindCorrelationData(0.0);

	public DelayWindCorrelationCoefficientWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public DelayWindCorrelationData getCorrelation()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWindCorrelation.getDelayWindCorrelation();
	}

	public void setDelayWindCorrelation(DelayWindCorrelation delayWindCorrelation)
	{
		this.delayWindCorrelation = delayWindCorrelation;
	}
}
