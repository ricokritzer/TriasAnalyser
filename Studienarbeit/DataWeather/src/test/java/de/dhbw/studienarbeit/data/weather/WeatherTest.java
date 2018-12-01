package de.dhbw.studienarbeit.data.weather;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.ServerNotAvailableException;
import de.dhbw.studienarbeit.data.helper.datamanagement.UpdateException;

public class WeatherTest
{
	final static String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><current><city id=\"2892794\" name=\"Karlsruhe\"><coord lon=\"8.4\" lat=\"49.01\"></coord><country>DE</country><sun rise=\"2018-10-17T05:50:25\" set=\"2018-10-17T16:32:06\"></sun></city><temperature value=\"17.59\" min=\"16\" max=\"19\" unit=\"metric\"></temperature><humidity value=\"67\" unit=\"%\"></humidity><pressure value=\"1019\" unit=\"hPa\"></pressure><wind><speed value=\"2.1\" name=\"Light breeze\"></speed><gusts></gusts><direction value=\"10\" code=\"N\" name=\"North\"></direction></wind><clouds value=\"36\" name=\"scattered clouds\"></clouds><visibility value=\"9000\"></visibility><precipitation mode=\"no\"></precipitation><weather number=\"802\" value=\"scattered clouds\" icon=\"03d\"></weather><lastupdate value=\"2018-10-17T10:20:00\"></lastupdate></current>";

	@Test
	public void testCoordinatesConstructor() throws Exception
	{
		final Weather coordinates = new Weather(49.01, 8.4);
		assertThat(coordinates.lat, Is.is(49.01));
		assertThat(coordinates.lon, Is.is(8.4));
	}

	@Test
	public void testCoordinatesXMLData() throws Exception
	{
		final Weather coordinates = new Weather(49.01, 8.4);
		coordinates.setData(xmlData);

		assertThat(coordinates.clouds, Is.is(36.0));
		assertThat(coordinates.temp, Is.is(17.59));
		assertThat(coordinates.humidity, Is.is(67.0));
		assertThat(coordinates.pressure, Is.is(1019.0));
		assertThat(coordinates.wind, Is.is(2.1));
		assertThat(coordinates.text, Is.is("scattered clouds"));
	}

	@Test
	void testModelIsReadable() throws Exception
	{
		final Weather coordinates = new Weather(49.01, 8.4);
		try
		{
			final ApiKey apiWeather = new ApiKey("9412f9cf01e1de32009e18c8276ea082", 2,
					"https://api.openweathermap.org/data/2.5/weather");
			coordinates.updateData(apiWeather);
		}
		catch (UpdateException | ServerNotAvailableException e)
		{
			fail("failed: " + e.getMessage());
		}
	}
}
