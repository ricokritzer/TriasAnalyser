package de.dhbw.studienarbeit.web.data.station;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.hamcrest.core.Is;
import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.database.DelayStationNeighbourDB;

public class DelayStationNeighbourWOTest
{
	@Test
	public void renewingData() throws Exception
	{
		final Position pos1 = new Position(0.0, 0.5);
		final Position pos2 = new Position(1.0, 1.5);
		final StationName name = new StationName("foo");

		final double avg1Old = 10;
		double avg2 = 20;
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB(name, pos1, avg1Old, name, pos2,
				avg2);

		final double avg1New = 15;
		avg2 = 12;
		final DelayStationNeighbourDB stationNeighbourDBLater = new DelayStationNeighbourDB(name, pos1, avg1New, name,
				pos2, avg2);

		final DelayStationNeighbourWO stationNeighbours = new DelayStationNeighbourWO(Optional.empty());

		assertThat(stationNeighbours.data.size(), Is.is(0));
		stationNeighbours.renewData(stationNeighbourDB);
		assertThat(stationNeighbours.data.size(), Is.is(1));
		assertThat(stationNeighbours.data.get(0).getAvg1(), Is.is(avg1Old));
		stationNeighbours.renewData(stationNeighbourDBLater);
		assertThat(stationNeighbours.data.size(), Is.is(1));
		assertThat(stationNeighbours.data.get(0).getAvg1(), Is.is(avg1New));
	}

}
