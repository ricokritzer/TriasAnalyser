package de.dhbw.studienarbeit.web.data.weather.wind;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWindCorrelation;
import de.dhbw.studienarbeit.data.reader.database.DelayWindCorrelationDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWindCorrelationCoefficientWO extends Updateable
{
	private DelayWindCorrelation data = new DelayWindCorrelation(0.0);

	public DelayWindCorrelationCoefficientWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public DelayWindCorrelation getCorrelation()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayWindCorrelationDB.getCorrelationCoefficient();
	}
}
