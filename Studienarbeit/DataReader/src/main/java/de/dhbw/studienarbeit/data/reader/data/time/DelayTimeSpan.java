package de.dhbw.studienarbeit.data.reader.data.time;

import java.io.IOException;
import java.util.List;

public interface DelayTimeSpan
{
	List<DelayTimeSpanData> getDelays() throws IOException;
}
