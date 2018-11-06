package de.dhbw.studienarbeit.data.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;
import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class Weather implements DataModel, Saveable
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

	public void updateData(final ApiKey apiKey) throws IOException
	{
		setNextUpdate(15);
		try
		{
			final URLConnection con = connectToAPI(apiKey);
			final String response = getResponse(con);
			LOGGER.log(Level.FINE, "Response: " + response);
			setData(response);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update data.", e);
			throw e;
		}
	}

	private URLConnection connectToAPI(final ApiKey apiKey) throws IOException
	{
		final URLConnection con = buildRequestURL(apiKey).openConnection();
		con.setDoOutput(true);
		con.setConnectTimeout(1000);
		con.setReadTimeout(3000);
		con.connect();
		return con;
	}

	private void setNextUpdate(final int nextUpdateInMinutes)
	{
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, nextUpdateInMinutes);
		nextUpdate = cal.getTime();
	}

	private URL buildRequestURL(final ApiKey apiKey) throws MalformedURLException
	{
		return new URL(new StringBuilder() //
				.append(apiKey.getUrl()).append("?") //
				.append("lat=").append(lat) //
				.append("&lon=").append(lon) //
				.append("&appid=").append(apiKey.getKey()) //
				.append("&mode=xml&units=metric") //
				.toString());
	}

	private String getResponse(URLConnection connection) throws IOException
	{
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

	public String getSQLQuerry()
	{
		final String seperator = ", ";
		return new StringBuilder() //
				.append("INSERT INTO Weather VALUES (") //
				.append(lat).append(seperator) //
				.append(lon).append(seperator) //
				.append(inQuotes(new Timestamp(date.getTime()).toString())).append(seperator) //
				.append(temp).append(seperator) //
				.append(humidity).append(seperator) //
				.append(pressure).append(seperator) //
				.append(wind).append(seperator) //
				.append(clouds).append(seperator) //
				.append(inQuotes(text)) //
				.append(");") //
				.toString();
	}

	private String inQuotes(String string)
	{
		return new StringBuilder().append("'").append(string).append("'").toString();
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
	public void updateAndSaveData(ApiKey apiKey) throws IOException
	{
		updateData(apiKey);
		DatabaseSaver.getInstance().save(this);
	}
}