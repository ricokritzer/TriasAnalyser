package de.dhbw.studienarbeit.data.reader.data.weather.text;

import java.io.IOException;
import java.util.List;

public interface DelayWeatherText
{
	List<DelayWeatherTextData> getDelays() throws IOException;
}
