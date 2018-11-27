package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StationNeighbourDBTest
{
	@Test
	void testSelecting() throws Exception
	{
		final List<StationDB> list = StationDB.getObservedStations();
		assertTrue(list.size() > 1);
	}
}
