package de.dhbw.studienarbeit.web.data.weather.symbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.DelayWeatherSymbolDB;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.WeatherSymbol;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWeatherSymbolWO extends Updateable
{
	private Delay<WeatherSymbol> delayWeatherSymbol = new DelayWeatherSymbolDB();

	private List<DelayData<WeatherSymbol>> data = new ArrayList<>();

	public DelayWeatherSymbolWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<WeatherSymbol>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWeatherSymbol.getDelays();
	}

	public void setDelayWeatherSymbol(Delay<WeatherSymbol> delayWeatherSymbol)
	{
		this.delayWeatherSymbol = delayWeatherSymbol;
		update();
	}
}
