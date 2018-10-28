package de.dhbw.studienarbeit.data.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import de.dhbw.studienarbeit.data.helper.ApiKey;
import de.dhbw.studienarbeit.data.helper.DataModel;

public class Weather implements DataModel
{
	private static final String URL_PRE = "https://api.openweathermap.org/data/2.5/weather?";

	// Responsemode could be html, xml or (default) JSON
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	protected String stationID;
	protected double lat;
	protected double lon;

	protected Date date;
	protected double temp;
	protected double humidity;
	protected double pressure;
	protected double wind;
	protected double clouds;

	private Date nextUpdate;

	public Weather(final String stationID, final double lat, final double lon)
	{
		this.stationID = stationID;
		this.lat = lat;
		this.lon = lon;
	}

	public void updateData(final ApiKey apiKey) throws IOException
	{
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 15);
		nextUpdate = cal.getTime();

		try
		{
			final URLConnection con = buildRequestURL(apiKey).openConnection();
			con.setDoOutput(true);
			con.setConnectTimeout(1000); // long timeout, but not infinite
			con.setReadTimeout(3000);
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);

			con.connect();
			waitForResponse(con);
		}
		catch (MalformedURLException e)
		{
			// ignore - never become true
		}
		catch (IOException e)
		{
			throw e;
		}
	}

	private URL buildRequestURL(final ApiKey apiKey) throws MalformedURLException
	{
		final String endUrl = "&appid=" + apiKey.getKey() + "&mode=xml&units=metric";
		final String dynamicURL = URL_PRE + "lat=" + lat + "&lon=" + lon + endUrl;
		return new URL(dynamicURL);
	}

	private void waitForResponse(URLConnection connection) throws IOException
	{
		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())))
		{
			final StringBuilder sbResponse = new StringBuilder();
			while (br.ready())
			{
				sbResponse.append(br.readLine());
			}
			setData(sbResponse.toString());
		}
	}

	protected void setData(final String response) throws IOException
	{
		temp = Double.valueOf(extractDate(response, "temperature"));
		humidity = Double.valueOf(extractDate(response, "humidity"));
		pressure = Double.valueOf(extractDate(response, "pressure"));
		wind = Double.valueOf(extractDate(response, "speed"));
		clouds = Double.valueOf(extractDate(response, "clouds"));
		date = new Date();
	}

	private String extractDate(final String xmlText, final String tag) throws IOException
	{
		try
		{
			final DocumentBuilder parser = factory.newDocumentBuilder();
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
	public String getSQLQuerry()
	{
		return "INSERT INTO Weather " + values("'" + stationID + "'", "'" + new Timestamp(date.getTime()) + "'", temp,
				humidity, pressure, wind, clouds);
	}

	private String values(Object... values)
	{
		final StringBuilder sb = new StringBuilder("VALUES (");
		Arrays.asList(values).forEach(e -> sb.append(e.toString() + ","));
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}

	@Override
	public Date nextUpdate()
	{
		return nextUpdate;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + " of " + stationID;
	}
}