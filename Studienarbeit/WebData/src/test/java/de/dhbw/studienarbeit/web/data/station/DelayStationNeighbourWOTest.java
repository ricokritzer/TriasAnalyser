package de.dhbw.studienarbeit.web.data.station;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.hamcrest.core.Is;
import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.database.DelayStationNeighbourDB;

public class DelayStationNeighbourWOTest
{
	@Test
	public void renewingData() throws Exception
	{
		final double lat1 = 0.0;
		final double lat2 = 1.0;
		final double lon1 = 0.5;
		final double lon2 = 1.5;

		final double avg1Old = 10;
		double avg2 = 20;
		final DelayStationNeighbourDB stationNeighbourDB = new DelayStationNeighbourDB("foo", lat1, lon1, avg1Old,
				"bar", lat2, lon2, avg2);

		final double avg1New = 15;
		avg2 = 12;
		final DelayStationNeighbourDB stationNeighbourDBLater = new DelayStationNeighbourDB("foo", lat1, lon1, avg1New,
				"bar", lat2, lon2, avg2);

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
