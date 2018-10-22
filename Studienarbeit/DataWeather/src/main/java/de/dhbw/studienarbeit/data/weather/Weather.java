package de.dhbw.studienarbeit.data.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import de.dhbw.studienarbeit.data.helper.DataModel;

public class Weather implements DataModel
{
	private static final Logger LOGGER = Logger.getLogger(Weather.class.getName());

	// Responsemode could be html, xml or (default) JSON
	private static final String URL_END = "&appid=b5923a1132896eba486d603bc6602a5f&mode=xml&units=metric";
	private static final String URL_PRE = "https://api.openweathermap.org/data/2.5/weather?";
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	protected double lat;
	protected double lon;

	protected double temp;
	protected double humidity;
	protected double pressure;
	protected double wind;
	protected double clouds;

	private URL requestURL;

	public Weather(final double lat, final double lon) throws IOException
	{
		this.lat = lat;
		this.lon = lon;

		final String dynamicURL = URL_PRE + "lat=" + lat + "&lon=" + lon + URL_END;
		requestURL = new URL(dynamicURL);
		updateData(3);
	}

	@Override
	public void updateData(final int attempts) throws IOException
	{
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
		final StringBuilder sb = new StringBuilder();
		sb.append(getPartialString("lon", lon));
		sb.append(getPartialString("lat", lat));
		sb.append(getPartialString("temp", temp));
		sb.append(getPartialString("humitidity", humidity));
		sb.append(getPartialString("pressure", pressure));
		sb.append(getPartialString("wind", wind));
		sb.append(getPartialString("clouds", clouds));
		LOGGER.log(Level.FINEST, "Weatherdata saved: " + sb.toString());
		return sb.toString();
	}

	private String getPartialString(final String text, final String value)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(text);
		sb.append(":\t");
		sb.append(value);
		return sb.toString();
	}

	private String getPartialString(final String text, final double value)
	{
		return getPartialString(text, "" + value);
	}

	@Override
	public Date nextUpdate()
	{
		Date now = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, 1);
		return now;
	}
}