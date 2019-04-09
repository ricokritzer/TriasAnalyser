package de.dhbw.studienarbeit.web.data.weather.wind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.CancelledStopsWind;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.CancelledStopsWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWind;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelation;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelationDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindCorrelationData;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindDB;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.Wind;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class WindWO extends Updateable
{
	private CancelledStopsWind updaterCancelledStops = new CancelledStopsWindDB();
	private DelayWindCorrelation updaterCorrelation = new DelayWindCorrelationDB();
	private DelayWind updaterDelays = new DelayWindDB();

	private List<CancelledStopsData<Wind>> cancelledStops = new ArrayList<>();
	private DelayWindCorrelationData correlation = new DelayWindCorrelationData(0.0);
	private List<DelayData<Wind>> delays = new ArrayList<>();

	public WindWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		cancelledStops = updaterCancelledStops.getCancelledStops();
		correlation = updaterCorrelation.getDelayWindCorrelation();
		delays = updaterDelays.getDelays();
	}

	public List<CancelledStopsData<Wind>> getCancelledStops()
	{
		return cancelledStops;
	}

	public DelayWindCorrelationData getCorrelation()
	{
		return correlation;
	}

	public List<DelayData<Wind>> getDelays()
	{
		return delays;
	}

	public void setUpdaterCancelledStops(CancelledStopsWind updaterCancelledStops)
	{
		this.updaterCancelledStops = updaterCancelledStops;
	}

	public void setUpdaterCorrelation(DelayWindCorrelation updaterCorrelation)
	{
		this.updaterCorrelation = updaterCorrelation;
	}

	public void setUpdaterDelays(DelayWind updaterDelays)
	{
		this.updaterDelays = updaterDelays;
	}
}
