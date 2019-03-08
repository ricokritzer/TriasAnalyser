package de.dhbw.studienarbeit.web.data.weather.symbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.weather.symbol.DelayWeatherSymbol;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.DelayWeatherSymbolDB;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.DelayWeatherSymbolData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWeatherSymbolWO extends Updateable
{
	private final DelayWeatherSymbol delayWeatherSymbol = new DelayWeatherSymbolDB();

	private List<DelayWeatherSymbolData> data = new ArrayList<>();

	public DelayWeatherSymbolWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayWeatherSymbolData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWeatherSymbol.getDelays();
	}
}
