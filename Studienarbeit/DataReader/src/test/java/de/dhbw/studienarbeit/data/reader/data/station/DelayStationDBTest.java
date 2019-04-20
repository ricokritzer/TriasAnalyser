package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.data.DelayDB;
import de.dhbw.studienarbeit.data.reader.data.DelayDBTest;

public class DelayStationDBTest extends DelayDBTest<StationData>
{
	@Override
	protected DelayDB<StationData> getClassUnderTest()
	{
		return new DelayStationDB();
	}
}
