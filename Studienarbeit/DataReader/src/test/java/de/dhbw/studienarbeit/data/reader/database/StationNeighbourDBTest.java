package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class StationNeighbourDBTest
{
	@Test
	void testSelecting() throws Exception
	{
		final List<StationNeighbourDB> list = StationNeighbourDB.getStationNeighbours();
		assertTrue(list.size() > 0);
	}

	@Test
	void testSlope() throws Exception
	{
		final StationNeighbourDB stationNeighbourDB = new StationNeighbourDB(0, 0, 0, 3, 4, 1);
		assertThat(stationNeighbourDB.calculateSlope(), Is.is(0.2));
	}

	@Test
	void testSlopeNegative() throws Exception
	{
		final StationNeighbourDB stationNeighbourDB = new StationNeighbourDB(3, 4, 0, 0, 0, 1);
		assertThat(stationNeighbourDB.calculateSlope(), Is.is(0.2));
	}
	
	@Test
	void testSlopeFive() throws Exception
	{
		final StationNeighbourDB stationNeighbourDB = new StationNeighbourDB(3, 4, 0, 0, 0, 5);
		assertThat(stationNeighbourDB.calculateSlope(), Is.is(1.0));
	}
	
	@Test
	void testSlopeLatLonDoesNotMatter() throws Exception
	{
		final StationNeighbourDB stationNeighbourDB = new StationNeighbourDB(4, 3, 0, 0, 0, 5);
		assertThat(stationNeighbourDB.calculateSlope(), Is.is(1.0));
	}

	@Test
	void testSlope0() throws Exception
	{
		final StationNeighbourDB stationNeighbourDB = new StationNeighbourDB(0, 0, 0, 0, 0, 0);
		assertThat(stationNeighbourDB.calculateSlope(), Is.is(Double.NaN));
	}
}
