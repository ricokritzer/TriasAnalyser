package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DelayCloudCorrelation;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelaysCloudsCorrelationCoefficient extends Updateable
{
	private double data = 0.0;

	public DelaysCloudsCorrelationCoefficient(Optional<DataUpdater> updater)
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
		data = DelayCloudCorrelation.getCorrelationCoefficient();
	}
}
