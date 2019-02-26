package de.dhbw.studienarbeit.web.data;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.hamcrest.core.Is;
import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;

public class StationNeighboursTest
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
		final StationNeighbourDB stationNeighbourDB = new StationNeighbourDB(lat1, lon1, avg1Old, lat2, lon2, avg2);

		final double avg1New = 15;
		avg2 = 12;
		final StationNeighbourDB stationNeighbourDBLater = new StationNeighbourDB(lat1, lon1, avg1New, lat2, lon2,
				avg2);

		final StationNeighbours stationNeighbours = new StationNeighbours(Optional.empty());

		assertThat(stationNeighbours.data.size(), Is.is(0));
		stationNeighbours.renewData(stationNeighbourDB);
		assertThat(stationNeighbours.data.size(), Is.is(1));
		assertThat(stationNeighbours.data.get(0).getAvg1(), Is.is(avg1Old));
		stationNeighbours.renewData(stationNeighbourDBLater);
		assertThat(stationNeighbours.data.size(), Is.is(1));
		assertThat(stationNeighbours.data.get(0).getAvg1(), Is.is(avg1New));
	}

}
