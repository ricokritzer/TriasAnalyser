package de.dhbw.studienarbeit.web.data.weather.humidity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.CancelledStopsHumidity;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.CancelledStopsHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidity;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.DelayHumidityDB;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.Humidity;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class HumidityWO extends Updateable
{
	private CancelledStopsHumidity updaterCancelledStops = new CancelledStopsHumidityDB();
	private DelayHumidityCorrelation updaterCorrelation = new DelayHumidityCorrelationDB();
	private DelayHumidity updaterDelays = new DelayHumidityDB();

	private List<CancelledStopsData<Humidity>> cancelledStops = new ArrayList<>();
	private DelayHumidityCorrelationData correlation = new DelayHumidityCorrelationData(0.0);
	private List<DelayData<Humidity>> delays = new ArrayList<>();

	public HumidityWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		cancelledStops = updaterCancelledStops.getCancelledStops();
		correlation = updaterCorrelation.getDelayHumidityCorrelation();
		delays = updaterDelays.getDelays();
	}

	public List<CancelledStopsData<Humidity>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayHumidityCorrelationData getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Humidity>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStopsHumidity updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayHumidityCorrelation updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(DelayHumidity updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
