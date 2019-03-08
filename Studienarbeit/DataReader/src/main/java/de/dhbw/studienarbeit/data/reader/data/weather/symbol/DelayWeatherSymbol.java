package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import java.io.IOException;
import java.util.List;

public interface DelayWeatherSymbol
{
	List<DelayWeatherSymbolData> getDelays() throws IOException;
}
