package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface DelayClouds
{
	List<DelayData<Clouds>> getDelays() throws IOException;
}
