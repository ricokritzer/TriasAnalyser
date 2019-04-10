package de.dhbw.studienarbeit.data.reader.data.weather;

import java.io.IOException;

public interface DelayCorrelation<T>
{
	DelayCorrelationData<T> getDelayCorrelation() throws IOException;
}
