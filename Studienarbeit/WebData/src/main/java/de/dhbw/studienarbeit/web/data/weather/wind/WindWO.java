package de.dhbw.studienarbeit.web.data.weather.wind;

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
import de.dhbw.studienarbeit.data.reader.data.weather.wind.CancelledStopsWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.Wind;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class WindWO extends Updateable
{
	private CancelledStops<Wind> updaterCancelledStops = new CancelledStopsWindDB();
	private DelayCorrelation<Wind> updaterCorrelation = new DelayWindCorrelationDB();
	private Delays<Wind> updaterDelays = new DelayWindDB();

	private List<CancelledStopsData<Wind>> cancelledStops = new ArrayList<>();
	private DelayCorrelationData<Wind> correlation = new DelayCorrelationData<>(0.0, Wind.class);
	private List<DelayData<Wind>> delays = new ArrayList<>();

	public WindWO(Optional<DataUpdater> updater)
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

	public List<CancelledStopsData<Wind>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayCorrelationData<Wind> getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Wind>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStops<Wind> updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayCorrelation<Wind> updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(Delays<Wind> updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
