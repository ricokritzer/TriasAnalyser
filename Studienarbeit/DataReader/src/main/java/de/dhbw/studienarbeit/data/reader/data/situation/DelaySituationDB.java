package de.dhbw.studienarbeit.data.reader.data.situation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DelaySituationDB implements DelaySituation
{
	@Override
	public List<DelaySituationData> getDelays() throws IOException
	{
		return new ArrayList<>();
	}
}
