package de.dhbw.studienarbeit.web.data.weather.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.text.DelayWeatherText;
import de.dhbw.studienarbeit.data.reader.data.weather.text.DelayWeatherTextDB;
import de.dhbw.studienarbeit.data.reader.data.weather.text.DelayWeatherTextData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWeatherTextWO extends Updateable
{
	private DelayWeatherText delayWeatherText = new DelayWeatherTextDB();

	private List<DelayWeatherTextData> data = new ArrayList<>();

	public DelayWeatherTextWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayWeatherTextData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWeatherText.getDelays();
	}

	public void setDelayWeatherText(DelayWeatherText delayWeatherText)
	{
		this.delayWeatherText = delayWeatherText;
	}
}
