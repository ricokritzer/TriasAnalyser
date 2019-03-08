package de.dhbw.studienarbeit.data.reader.data.time;

import java.io.IOException;
import java.util.List;

public interface DelayHour
{
	List<DelayHourData> getDelays() throws IOException;
}
