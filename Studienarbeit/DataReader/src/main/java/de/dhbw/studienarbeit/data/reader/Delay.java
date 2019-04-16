package de.dhbw.studienarbeit.data.reader;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface Delay<T>
{
	List<DelayData<T>> getDelays() throws IOException;
}
