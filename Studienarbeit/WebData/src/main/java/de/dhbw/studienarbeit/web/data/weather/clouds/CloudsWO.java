package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.CancelledStopsClouds;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.CancelledStopsCloudsDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.Clouds;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayClouds;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CloudsWO extends Updateable
{
	private CancelledStopsClouds updaterCancelledStops = new CancelledStopsCloudsDB();
	private DelayCloudCorrelation updaterCorrelation = new DelayCloudCorrelationDB();
	private DelayClouds updaterDelays = new DelayCloudsDB();

	private List<CancelledStopsData<Clouds>> cancelledStops = new ArrayList<>();
	private DelayCloudCorrelationData correlation = new DelayCloudCorrelationData(0.0);
	private List<DelayData<Clouds>> delays = new ArrayList<>();

	public CloudsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		cancelledStops = updaterCancelledStops.getCancelledStops();
		correlation = updaterCorrelation.getDelayCloudCorrelation();
		delays = updaterDelays.getDelays();
	}

	public List<CancelledStopsData<Clouds>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayCloudCorrelationData getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Clouds>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStopsClouds updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayCloudCorrelation updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(DelayClouds updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
