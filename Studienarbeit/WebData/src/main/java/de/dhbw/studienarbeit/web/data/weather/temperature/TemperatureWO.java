package de.dhbw.studienarbeit.web.data.weather.temperature;

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
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.CancelledStopsTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.Temperature;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class TemperatureWO extends Updateable
{
	private CancelledStops<Temperature> updaterCancelledStops = new CancelledStopsTemperatureDB();
	private DelayCorrelation<Temperature> updaterCorrelation = new DelayTemperatureCorrelationDB();
	private Delays<Temperature> updaterDelays = new DelayTemperatureDB();

	private List<CancelledStopsData<Temperature>> cancelledStops = new ArrayList<>();
	private DelayCorrelationData<Temperature> correlation = new DelayCorrelationData<>(0.0, Temperature.class);
	private List<DelayData<Temperature>> delays = new ArrayList<>();

	public TemperatureWO(Optional<DataUpdater> updater)
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

	public List<CancelledStopsData<Temperature>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayCorrelationData<Temperature> getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Temperature>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStops<Temperature> updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayCorrelation<Temperature> updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(Delays<Temperature> updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
