package de.dhbw.studienarbeit.web.data.weather.pressure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.CancelledStopsPressure;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.CancelledStopsPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressure;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.DelayPressureDB;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.Pressure;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class PressureWO extends Updateable
{
	private CancelledStopsPressure updaterCancelledStops = new CancelledStopsPressureDB();
	private DelayPressureCorrelation updaterCorrelation = new DelayPressureCorrelationDB();
	private DelayPressure updaterDelays = new DelayPressureDB();

	private List<CancelledStopsData<Pressure>> cancelledStops = new ArrayList<>();
	private DelayPressureCorrelationData correlation = new DelayPressureCorrelationData(0.0);
	private List<DelayData<Pressure>> delays = new ArrayList<>();

	public PressureWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		cancelledStops = updaterCancelledStops.getCancelledStops();
		correlation = updaterCorrelation.getDelayPressureCorrelation();
		delays = updaterDelays.getDelays();
	}

	public List<CancelledStopsData<Pressure>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayPressureCorrelationData getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Pressure>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStopsPressure updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayPressureCorrelation updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(DelayPressure updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
