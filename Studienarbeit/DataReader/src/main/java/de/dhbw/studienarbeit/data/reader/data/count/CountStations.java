package de.dhbw.studienarbeit.data.reader.data.count;

public interface CountStations extends Count
{
	@Deprecated
	CountData countStations();

	@Deprecated
	CountData countStationsWithRealtimeData();

	@Deprecated
	CountData countObservedStations();
}
