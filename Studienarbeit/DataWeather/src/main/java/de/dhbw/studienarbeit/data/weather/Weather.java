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
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import de.dhbw.studienarbeit.data.helper.ConfigurationData;
import de.dhbw.studienarbeit.data.helper.DataModel;

public class Weather implements DataModel
{
	private static final Logger LOGGER = Logger.getLogger(Weather.class.getName());

	// Responsemode could be html, xml or (default) JSON
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	public static final String SQL_TABLE_WEATHER = "CREATE TABLE Weather (stopID varchar(20), timeStamp timestamp, temp decimal(6,2), humidity decimal(6,2),pressure decimal(6,2), wind decimal(6,2), clouds decimal(6,2), primary key (stopID, timeStamp));";

	protected String stopID;
	protected double lat;
	protected double lon;

	protected Date date;
	protected double temp;
	protected double humidity;
	protected double pressure;
	protected double wind;
	protected double clouds;

	private Date nextUpdate;

	private URL requestURL;

	public Weather(final String stopID, final double lat, final double lon) throws IOException
	{
		this.stopID = stopID;
		this.lat = lat;
		this.lon = lon;

		final String dynamicURL = ConfigurationData.URL_PRE + "lat=" + lat + "&lon=" + lon + ConfigurationData.URL_END;
		requestURL = new URL(dynamicURL);
		updateData(3);
	}

	@Override
	public void updateData(final int attempts) throws IOException
	{
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 1);
		nextUpdate = cal.getTime();

		try
		{
			final URLConnection con = requestURL.openConnection();
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
			if (attempts > 1)
			{
				updateData(attempts - 1);
			}
			else
			{
				throw e;
			}
		}
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
		String sql = "INSERT INTO Weather " + values("'" + stopID + "'", "'" + new Timestamp(date.getTime()) + "'",
				temp, humidity, pressure, wind, clouds);
		System.out.println(sql);
		return sql;
	}

	private String values(Object... values)
	{
		StringBuilder sb = new StringBuilder("VALUES (");
		Arrays.asList(values).forEach(e -> sb.append(e + ","));
		sb.deleteCharAt(sb.length() - 1);
		sb.append(");");
		return sb.toString();
	}

	@Override
	public Date nextUpdate()
	{
		return nextUpdate;
	}
}