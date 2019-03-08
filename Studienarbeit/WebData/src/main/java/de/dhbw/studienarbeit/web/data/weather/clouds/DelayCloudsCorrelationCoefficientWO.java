package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayCloudsCorrelationCoefficientWO extends Updateable
{
	private final DelayCloudCorrelation delayCloudCorrelation = new DelayCloudCorrelationDB();

	private DelayCloudCorrelationData data = new DelayCloudCorrelationData(0.0);

	public DelayCloudsCorrelationCoefficientWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public DelayCloudCorrelationData getCorrelation()
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
		data = delayCloudCorrelation.getDelayCloudsCorrelation();
	}
}
