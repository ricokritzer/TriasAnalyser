package de.dhbw.studienarbeit.data.reader.data.count;

public interface CountStations
{
	CountData countStations();

	CountData countStationsWithRealtimeData();
	
	CountData countObservedStations();
}
