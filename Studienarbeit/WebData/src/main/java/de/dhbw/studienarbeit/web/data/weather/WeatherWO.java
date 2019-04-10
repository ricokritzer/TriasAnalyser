package de.dhbw.studienarbeit.web.data.weather;

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
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public abstract class WeatherWO<T> extends Updateable
{
	protected CancelledStops<T> updaterCancelledStops;
	protected DelayCorrelation<T> updaterCorrelation;
	protected Delays<T> updaterDelays;

	private List<CancelledStopsData<T>> cancelledStops = new ArrayList<>();
	private DelayCorrelationData<T> correlation;
	private List<DelayData<T>> delays = new ArrayList<>();

	public WeatherWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		updaterCancelledStops = getDefaultUpdaterCancelledStops();
		updaterCorrelation = getDefaultUpdaterCorrelation();
		updaterDelays = getDefaultUpdaterDelays();

		cancelledStops = getDefaultCancelledStops();
		correlation = getDefaultCorrelation();
		delays = getDefaultDelays();

		cancelledStops = updaterCancelledStops.getCancelledStops();
		correlation = updaterCorrelation.getDelayCorrelation();
		delays = updaterDelays.getDelays();
	}

	public List<CancelledStopsData<T>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayCorrelationData<T> getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<T>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStops<T> updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayCorrelation<T> updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(Delays<T> updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}

	protected List<DelayData<T>> getDefaultDelays()
	{
		return new ArrayList<>();
	}

	protected List<CancelledStopsData<T>> getDefaultCancelledStops()
	{
		return new ArrayList<>();
	}

	protected abstract Delays<T> getDefaultUpdaterDelays();

	protected abstract DelayCorrelation<T> getDefaultUpdaterCorrelation();

	protected abstract CancelledStops<T> getDefaultUpdaterCancelledStops();

	protected abstract DelayCorrelationData<T> getDefaultCorrelation();
}
