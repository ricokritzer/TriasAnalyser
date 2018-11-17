package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class StationDBTest
{
	@Test
	public void testGetAllObservedStations() throws Exception
	{
		final List<StationDB> list = StationDB.getObservedStations();
		assertTrue(list.size() > 1);
	}

	@Test
	public void testGetObservedStationsOfKVV() throws Exception
	{
		final Operator operator = new Operator("kvv");
		final List<StationDB> list = StationDB.getObservedStations(operator);
		assertTrue(list.size() > 1);
	}
}
