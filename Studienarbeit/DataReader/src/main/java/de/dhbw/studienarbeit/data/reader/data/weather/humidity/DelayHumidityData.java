package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface DelayHumidityData extends DelayData
{
	double getHumidity();
}
