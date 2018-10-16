package de.dhbw.studienarbeit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Coordinates
{
	private final String urlEnd = "&appid=b5923a1132896eba486d603bc6602a5f";
	private final String urlPre = "https://api.openweathermap.org/data/2.5/weather?";

	private double lat;
	private double lon;

	private URL requestURL;

	public Coordinates(final double lat, final double lon)
	{
		this.lat = lat;
		this.lon = lon;

		try
		{
			final String dynamicURL = urlPre + "lat=" + lat + "&lon=" + lon + urlEnd;
			requestURL = new URL(dynamicURL);
			updateData();
		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
		}
	}

	public void updateData() throws IOException
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

	private void setData(final String response)
	{
		// Gson gson = new Gson();
		System.out.println(response);
	}
}