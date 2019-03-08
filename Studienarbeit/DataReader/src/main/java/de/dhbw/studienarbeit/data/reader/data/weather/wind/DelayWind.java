package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;
import java.util.List;

public interface DelayWind
{
	List<DelayWindData> getDelays() throws IOException;
}
