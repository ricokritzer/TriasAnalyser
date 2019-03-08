package de.dhbw.studienarbeit.data.reader.data.line;

import java.io.IOException;
import java.util.List;

public interface DelayLine
{
	List<DelayLineData> getDelays() throws IOException;
}
