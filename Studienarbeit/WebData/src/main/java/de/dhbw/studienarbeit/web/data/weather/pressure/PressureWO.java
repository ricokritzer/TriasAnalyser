package de.dhbw.studienarbeit.web.data.weather.pressure;

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
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.CancelledStopsPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.Pressure;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class PressureWO extends Updateable
{
	private CancelledStops<Pressure> updaterCancelledStops = new CancelledStopsPressureDB();
	private DelayCorrelation<Pressure> updaterCorrelation = new DelayPressureCorrelationDB();
	private Delays<Pressure> updaterDelays = new DelayPressureDB();

	private List<CancelledStopsData<Pressure>> cancelledStops = new ArrayList<>();
	private DelayCorrelationData<Pressure> correlation = new DelayCorrelationData<>(0.0, Pressure.class);
	private List<DelayData<Pressure>> delays = new ArrayList<>();

	public PressureWO(Optional<DataUpdater> updater)
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

	public List<CancelledStopsData<Pressure>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayCorrelationData<Pressure> getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Pressure>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStops<Pressure> updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayCorrelation<Pressure> updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(Delays<Pressure> updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
