package de.dhbw.studienarbeit.web.data.weather.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.Delays;
import de.dhbw.studienarbeit.data.reader.data.weather.text.DelayWeatherTextDB;
import de.dhbw.studienarbeit.data.reader.data.weather.text.WeatherText;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWeatherTextWO extends Updateable
{
	private Delays<WeatherText> delayWeatherText = new DelayWeatherTextDB();

	private List<DelayData<WeatherText>> data = new ArrayList<>();

	public DelayWeatherTextWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<WeatherText>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWeatherText.getDelays();
	}

	public void setDelayWeatherText(Delays<WeatherText> delayWeatherText)
	{
		this.delayWeatherText = delayWeatherText;
		update();
	}
}
