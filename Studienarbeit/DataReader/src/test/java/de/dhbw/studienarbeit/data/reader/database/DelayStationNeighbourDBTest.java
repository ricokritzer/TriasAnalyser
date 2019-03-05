package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;

public class DelayStationNeighbourDBTest
{
	private final StationName name = new StationName("foo");

	@Test
	void testSlope() throws Exception
	{
		final Position pos1 = new Position(0, 0);
		final Position pos2 = new Position(3, 4);
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(name, pos1, 0, name, pos2, 1);

		assertThat(stationNeighbourDB.getSlope(), Is.is(0.2));
	}

	@Test
	void testSlopeNegative() throws Exception
	{
		final Position pos1 = new Position(3, 4);
		final Position pos2 = new Position(0, 0);
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(name, pos1, 0, name, pos2, 1);

		assertThat(stationNeighbourDB.getSlope(), Is.is(0.2));
	}

	@Test
	void testSlopeFive() throws Exception
	{
		final Position pos1 = new Position(3, 4);
		final Position pos2 = new Position(0, 0);
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(name, pos1, 0, name, pos2, 5);

		assertThat(stationNeighbourDB.getSlope(), Is.is(1.0));
	}

	@Test
	void testSlopeLatLonDoesNotMatter() throws Exception
	{
		final Position pos1 = new Position(4, 3);
		final Position pos2 = new Position(0, 0);
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(name, pos1, 0, name, pos2, 5);

		assertThat(stationNeighbourDB.getSlope(), Is.is(1.0));
	}

	@Test
	void testSlope0() throws Exception
	{
		final Position pos1 = new Position(0, 0);
		final Position pos2 = new Position(0, 0);
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(name, pos1, 0, name, pos2, 0);

		assertThat(stationNeighbourDB.getSlope(), Is.is(Double.NaN));
	}
}
