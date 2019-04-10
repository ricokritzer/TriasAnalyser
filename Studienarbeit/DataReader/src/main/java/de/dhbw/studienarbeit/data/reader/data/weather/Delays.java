package de.dhbw.studienarbeit.data.reader.data.weather;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface Delays<T>
{
	List<DelayData<T>> getDelays() throws IOException;
}
