package de.dhbw.studienarbeit.data.reader.data.station;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;

public class DelayStationNeighbourDBTest
{
	private final StationName name = new StationName("foo");

	@Test
	void testSlope() throws Exception
	{
		final Position pos1 = new Position(0, 0);
		final Position pos2 = new Position(3, 4);
		final DelayAverage avg1 = new DelayAverage(0.0);
		final DelayAverage avg2 = new DelayAverage(1.0);

		final DelayStationNeighbourData stationNeighbour = new DelayStationNeighbourData(name, pos1, avg1, name, pos2,
				avg2);

		assertThat(stationNeighbour.getSlope(), Is.is(0.2));
	}

	@Test
	void testSlopeNegative() throws Exception
	{
		final Position pos1 = new Position(3, 4);
		final Position pos2 = new Position(0, 0);
		final DelayAverage avg1 = new DelayAverage(0.0);
		final DelayAverage avg2 = new DelayAverage(1.0);

		final DelayStationNeighbourData stationNeighbour = new DelayStationNeighbourData(name, pos1, avg1, name, pos2,
				avg2);

		assertThat(stationNeighbour.getSlope(), Is.is(0.2));
	}

	@Test
	void testSlopeFive() throws Exception
	{
		final Position pos1 = new Position(3, 4);
		final Position pos2 = new Position(0, 0);
		final DelayAverage avg1 = new DelayAverage(0.0);
		final DelayAverage avg2 = new DelayAverage(5.0);

		final DelayStationNeighbourData stationNeighbour = new DelayStationNeighbourData(name, pos1, avg1, name, pos2,
				avg2);

		assertThat(stationNeighbour.getSlope(), Is.is(1.0));
	}

	@Test
	void testSlopeLatLonDoesNotMatter() throws Exception
	{
		final Position pos1 = new Position(4, 3);
		final Position pos2 = new Position(0, 0);
		final DelayAverage avg1 = new DelayAverage(0.0);
		final DelayAverage avg2 = new DelayAverage(5.0);

		final DelayStationNeighbourData stationNeighbour = new DelayStationNeighbourData(name, pos1, avg1, name, pos2,
				avg2);

		assertThat(stationNeighbour.getSlope(), Is.is(1.0));
	}

	@Test
	void testSlope0() throws Exception
	{
		final Position pos1 = new Position(0, 0);
		final Position pos2 = new Position(0, 0);
		final DelayAverage avg1 = new DelayAverage(0.0);
		final DelayAverage avg2 = new DelayAverage(0.0);

		final DelayStationNeighbourData stationNeighbourData = new DelayStationNeighbourData(name, pos1, avg1, name,
				pos2, avg2);

		assertThat(stationNeighbourData.getSlope(), Is.is(Double.NaN));
	}
}
