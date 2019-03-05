package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class DelayStationNeighbourDBTest
{
	@Test
	void testSlope() throws Exception
	{
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(0, 0, 0, 3, 4, 1);
		assertThat(stationNeighbourDB.getSlope(), Is.is(0.2));
	}

	@Test
	void testSlopeNegative() throws Exception
	{
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(3, 4, 0, 0, 0, 1);
		assertThat(stationNeighbourDB.getSlope(), Is.is(0.2));
	}

	@Test
	void testSlopeFive() throws Exception
	{
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(3, 4, 0, 0, 0, 5);
		assertThat(stationNeighbourDB.getSlope(), Is.is(1.0));
	}

	@Test
	void testSlopeLatLonDoesNotMatter() throws Exception
	{
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(4, 3, 0, 0, 0, 5);
		assertThat(stationNeighbourDB.getSlope(), Is.is(1.0));
	}

	@Test
	void testSlope0() throws Exception
	{
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(0, 0, 0, 0, 0, 0);
		assertThat(stationNeighbourDB.getSlope(), Is.is(Double.NaN));
	}
}
