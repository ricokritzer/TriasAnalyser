package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CountTest
{
	@Test
	void countLines() throws Exception
	{
		Count count = Count.countLines();
		assertNotEquals(count, Count.UNABLE_TO_COUNT);
	}

	@Test
	void countStations() throws Exception
	{
		Count count = Count.countStations();
		assertNotEquals(count, Count.UNABLE_TO_COUNT);
	}

	@Test
	void countStationsWithRealtimeData() throws Exception
	{
		Count count = Count.countStationsWithRealtimeData();
		assertNotEquals(count, Count.UNABLE_TO_COUNT);
	}

	@Test
	void countObservedStations() throws Exception
	{
		Count count = Count.countObservedStations();
		assertNotEquals(count, Count.UNABLE_TO_COUNT);
	}

	@Test
	void countWeather() throws Exception
	{
		Count count = Count.countWeather();
		assertNotEquals(count, Count.UNABLE_TO_COUNT);
	}

	@Test
	void countStops() throws Exception
	{
		Count count = Count.countStops();
		assertNotEquals(count, Count.UNABLE_TO_COUNT);
	}
}
