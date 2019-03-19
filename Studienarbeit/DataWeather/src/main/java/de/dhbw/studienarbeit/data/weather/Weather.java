package de.dhbw.studienarbeit.data.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKeyData;
import de.dhbw.studienarbeit.data.helper.datamanagement.Manageable;
import de.dhbw.studienarbeit.data.helper.datamanagement.ServerNotAvailableException;
import de.dhbw.studienarbeit.data.helper.datamanagement.UpdateException;

public class Weather implements Manageable, Saveable
{
	private static final Logger LOGGER = Logger.getLogger(Weather.class.getName());
	private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();

	protected double lat;
	protected double lon;

	protected Date date;
	protected double temp;
	protected double humidity;
	protected double pressure;
	protected double wind;
	protected double clouds;
	protected String text;

	private Date nextUpdate;

	public Weather(final double lat, final double lon)
	{
		this.lat = round(lat, 2);
		this.lon = round(lon, 2);
	}

	protected void updateData(final ApiKeyData apiKey) throws UpdateException, ServerNotAvailableException
	{
		try
		{
			final HttpURLConnection con = connectToAPI(apiKey);
			final String response = getResponse(con);
			LOGGER.log(Level.FINE, "Response: " + response);
			setData(response);
			setNextUpdate(30);
		}
		catch (IOException e)
		{
			setNextUpdate(0);
			throw new UpdateException("Unable to update data: " + this.toString(), e);
		}
	}

	private HttpURLConnection connectToAPI(final ApiKeyData apiKey) throws IOException
	{
		final URL url = buildRequestURL(apiKey);
		final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setConnectTimeout(1000);
		connection.setReadTimeout(3000);
		connection.connect();

		return connection;
	}

	private void setNextUpdate(final int nextUpdateInMinutes)
	{
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, nextUpdateInMinutes);
		nextUpdate = cal.getTime();
	}

	private URL buildRequestURL(final ApiKeyData apiKey) throws MalformedURLException
	{
		return new URL(new StringBuilder() //
				.append(apiKey.getUrl()).append("?") //
				.append("lat=").append(lat) //
				.append("&lon=").append(lon) //
				.append("&appid=").append(apiKey.getKey()) //
				.append("&mode=xml&units=metric") //
				.toString());
	}

	private String getResponse(HttpURLConnection connection)
			throws IOException, ServerNotAvailableException, UpdateException
	{
		int statusCode = connection.getResponseCode();

		if (statusCode == 503)
		{
			throw new ServerNotAvailableException("Weather server is not available. Status Code 503");
		}
		if (statusCode == 408 || statusCode == 504)
		{
			throw new UpdateException("Timeout");
		}
		if (statusCode == 500)
		{
			throw new UpdateException("Internal server error - will work fine next request");
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())))
		{
			final StringBuilder sbResponse = new StringBuilder();
			while (br.ready())
			{
				sbResponse.append(br.readLine());
			}
			return sbResponse.toString();
		}
	}

	protected void setData(final String response) throws IOException
	{
		temp = Double.valueOf(extractDate(response, "temperature"));
		humidity = Double.valueOf(extractDate(response, "humidity"));
		pressure = Double.valueOf(extractDate(response, "pressure"));
		wind = Double.valueOf(extractDate(response, "speed"));
		clouds = Double.valueOf(extractDate(response, "clouds"));
		text = extractDate(response, "weather");
		date = new Date();
	}

	private String extractDate(final String xmlText, final String tag) throws IOException
	{
		try
		{
			final DocumentBuilder parser = FACTORY.newDocumentBuilder();
			final Document doc = parser.parse(new InputSource(new StringReader(xmlText)));
			final Node node = doc.getElementsByTagName(tag).item(0).getAttributes().getNamedItem("value");

			return node.getNodeValue();
		}
		catch (Exception e)
		{
			throw new IOException("Error in xml: Tag '" + tag + "' with parameter 'value' not found", e);
		}
	}

	@Override
	public Date nextUpdate()
	{
		return nextUpdate;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + " of lat=" + lat + " & lon=" + lon;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(lat, lon);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Weather)
		{
			return sameCoordinates((Weather) obj);
		}
		return super.equals(obj);
	}

	public boolean sameCoordinates(Weather w)
	{
		return this.lat == w.lat && this.lon == w.lon;
	}

	private static double round(double value, int decimals)
	{
		return Math.round(value * Math.pow(10, decimals)) / Math.pow(10, decimals);
	}

	@Override
	public void updateAndSaveData(ApiKeyData apiKey) throws UpdateException, ServerNotAvailableException
	{
		try
		{
			updateData(apiKey);
			DatabaseSaver.saveData(this);
		}
		catch (UpdateException e)
		{
			LOGGER.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Weather (lat, lon, timeStamp, temp, humidity, pressure, wind, clouds, weatherInformationID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM WeatherInformation WHERE text = ?));";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setDouble(1, lat);
		preparedStatement.setDouble(2, lon);

		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, 1);
		preparedStatement.setTimestamp(3, new Timestamp(c.getTimeInMillis()));
		preparedStatement.setDouble(4, temp);
		preparedStatement.setDouble(5, humidity);
		preparedStatement.setDouble(6, pressure);
		preparedStatement.setDouble(7, wind);
		preparedStatement.setDouble(8, clouds);
		preparedStatement.setString(9, text);
	}
}