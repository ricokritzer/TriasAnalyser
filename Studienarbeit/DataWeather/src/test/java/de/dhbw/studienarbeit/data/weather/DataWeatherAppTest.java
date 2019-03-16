package de.dhbw.studienarbeit.data.weather;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.data.station.ObservedStationData;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;

public class DataWeatherAppTest
{
	@Test
	void testConvertToList() throws Exception
	{
		final ObservedStationData station = new ObservedStationData(new StationID("foo"), new StationName("bar"),
				new Position(49.0182304023, 8.40232424), new OperatorID("bla"));
		final ObservedStationData stationWithSimilarWeather = new ObservedStationData(new StationID("foo2"),
				new StationName("bar2"), new Position(49.01923123, 8.40432424), new OperatorID("bla2"));

		final List<ObservedStationData> stations = new ArrayList<>();
		stations.add(station);
		stations.add(stationWithSimilarWeather);

		final DataWeatherApp app = new DataWeatherApp();
		final List<Weather> weather = app.convertToWeather(stations);

		assertThat(weather.size(), Is.is(1));
	}

}
