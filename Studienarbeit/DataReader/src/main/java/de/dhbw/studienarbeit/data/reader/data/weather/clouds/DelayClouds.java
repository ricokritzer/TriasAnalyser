package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.util.List;

public interface DelayClouds
{
	List<DelayCloudsData> getDelays() throws IOException;
}
