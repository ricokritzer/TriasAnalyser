package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface DelayWind
{
	List<DelayData<Wind>> getDelays() throws IOException;
}
