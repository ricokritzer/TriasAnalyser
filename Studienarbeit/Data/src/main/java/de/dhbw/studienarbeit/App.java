package de.dhbw.studienarbeit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class App
{
	private static final String URL_TRIAS = "http://185.201.144.208/kritzertrias/trias";
	private final XMLCreator xmlCreator = new XMLCreator();

	public static void main(String[] args)
	{
		try
		{
			new App();
		}
		catch (IOException e)
		{
			e.printStackTrace(System.out);
		}
	}

	public App() throws IOException
	{
		final String stopIDPre = "de:8212:";
		for (int idx = 1; idx < 10; idx++)
		{
			final String stopID = stopIDPre + idx;
			final URLConnection con = createConnection();
			request(con, xmlCreator.getRequestXML(stopID));
			waitForResponse(con);
		}
	}

	private void waitForResponse(final URLConnection connection) throws IOException
	{
		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())))
		{
			final StringBuilder sbResponse = new StringBuilder();
			while (br.ready())
			{
				sbResponse.append(br.readLine());
			}
			Response.printAsync(sbResponse.toString());
		}
	}

	private void request(final URLConnection connection, final String request) throws IOException
	{
		final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(request);
		writer.flush();
		writer.close();
	}

	private URLConnection createConnection() throws IOException
	{
		final URL url = new URL(URL_TRIAS);
		final URLConnection con = url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(1000); // long timeout, but not infinite
		con.setReadTimeout(3000);
		con.setUseCaches(false);
		con.setDefaultUseCaches(false);
		con.setRequestProperty("Content-Type", "text/xml");
		return con;
	}
}
