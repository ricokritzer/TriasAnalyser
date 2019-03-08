package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudsCorrelation;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayCloudsCorrelationCoefficientWO extends Updateable
{
	private DelayCloudsCorrelation data = new DelayCloudsCorrelation(0.0);

	public DelayCloudsCorrelationCoefficientWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public DelayCloudsCorrelation getCorrelation()
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
		data = DelayCloudCorrelationDB.getDelayCloudsCorrelation();
	}
}
