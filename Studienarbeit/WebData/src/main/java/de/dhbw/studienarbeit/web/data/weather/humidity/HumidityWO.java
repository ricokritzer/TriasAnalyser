package de.dhbw.studienarbeit.web.data.weather.humidity;

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
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.CancelledStopsHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.Humidity;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class HumidityWO extends Updateable
{
	private CancelledStops<Humidity> updaterCancelledStops = new CancelledStopsHumidityDB();
	private DelayCorrelation<Humidity> updaterCorrelation = new DelayHumidityCorrelationDB();
	private Delays<Humidity> updaterDelays = new DelayHumidityDB();

	private List<CancelledStopsData<Humidity>> cancelledStops = new ArrayList<>();
	private DelayCorrelationData<Humidity> correlation = new DelayCorrelationData<Humidity>(0.0, Humidity.class);
	private List<DelayData<Humidity>> delays = new ArrayList<>();

	public HumidityWO(Optional<DataUpdater> updater)
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

	public List<CancelledStopsData<Humidity>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayCorrelationData<Humidity> getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Humidity>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStops<Humidity> updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayCorrelation<Humidity> updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(Delays<Humidity> updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
