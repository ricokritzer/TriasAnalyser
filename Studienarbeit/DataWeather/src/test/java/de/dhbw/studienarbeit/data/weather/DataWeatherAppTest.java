package de.dhbw.studienarbeit.data.weather;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.reader.database.StationDB;

public class DataWeatherAppTest
{
	@Test
	void testConvertToList() throws Exception
	{
		final StationDB station = new StationDB("foo", "bar", 49.0182304023, 8.40232424, "bla", true);
		final StationDB stationWithSimilarWeather = new StationDB("foo2", "bar2", 49.01923123, 8.40432424, "bla2", true);

		final List<StationDB> stations = new ArrayList<>();
		stations.add(station);
		stations.add(stationWithSimilarWeather);

		final DataWeatherApp app = new DataWeatherApp();
		final List<Weather> weather = app.convertToWeather(stations);

		assertThat(weather.size(), Is.is(1));
	}

}
