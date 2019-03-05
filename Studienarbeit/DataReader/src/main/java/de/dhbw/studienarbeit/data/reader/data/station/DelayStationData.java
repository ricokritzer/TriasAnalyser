package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public interface DelayStationData extends StationData, DelayData
{
	int getCount();
}
