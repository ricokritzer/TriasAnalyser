package de.dhbw.studienarbeit.data.reader.data.situation;

import java.io.IOException;
import java.util.List;

public interface DelaySituation
{
	List<DelaySituationData> getDelays() throws IOException;
}
