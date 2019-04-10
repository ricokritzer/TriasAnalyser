package de.dhbw.studienarbeit.web.data.weather.clouds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStops;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.Delays;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.CancelledStopsCloudsDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.Clouds;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.DelayCloudsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class CloudsWO extends Updateable
{
	private CancelledStops<Clouds> updaterCancelledStops = new CancelledStopsCloudsDB();
	private DelayCorrelation<Clouds> updaterCorrelation = new DelayCloudCorrelationDB();
	private Delays<Clouds> updaterDelays = new DelayCloudsDB();

	private List<CancelledStopsData<Clouds>> cancelledStops = new ArrayList<>();
	private DelayCorrelationData<Clouds> correlation = new DelayCorrelationData<>(0.0, Clouds.class);
	private List<DelayData<Clouds>> delays = new ArrayList<>();

	public CloudsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		cancelledStops = updaterCancelledStops.getCancelledStops();
		correlation = updaterCorrelation.getDelayCorrelation();
		delays = updaterDelays.getDelays();
	}

	public List<CancelledStopsData<Clouds>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayCorrelationData<Clouds> getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Clouds>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStops<Clouds> updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayCorrelation<Clouds> updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(Delays<Clouds> updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
