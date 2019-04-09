package de.dhbw.studienarbeit.web.data.weather.temperature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.CancelledStopsTemperature;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.CancelledStopsTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperature;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.DelayTemperatureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.Temperature;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class TemperatureWO extends Updateable
{
	private CancelledStopsTemperature updaterCancelledStops = new CancelledStopsTemperatureDB();
	private DelayTemperatureCorrelation updaterCorrelation = new DelayTemperatureCorrelationDB();
	private DelayTemperature updaterDelays = new DelayTemperatureDB();

	private List<CancelledStopsData<Temperature>> cancelledStops = new ArrayList<>();
	private DelayTemperatureCorrelationData correlation = new DelayTemperatureCorrelationData(0.0);
	private List<DelayData<Temperature>> delays = new ArrayList<>();

	public TemperatureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		cancelledStops = updaterCancelledStops.getCancelledStops();
		correlation = updaterCorrelation.getDelayTemperatureCorrelation();
		delays = updaterDelays.getDelays();
	}

	public List<CancelledStopsData<Temperature>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayTemperatureCorrelationData getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Temperature>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStopsTemperature updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayTemperatureCorrelation updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(DelayTemperature updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
