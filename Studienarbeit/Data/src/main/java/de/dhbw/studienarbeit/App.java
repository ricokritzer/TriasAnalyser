package de.dhbw.studienarbeit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class App
{
	public static void main(String[] args)
	{
		int errors = 0;

		final String stopIDPre = "de:8212:";
		final XMLCreator xmlCreator = new XMLCreator();

		for (int idx = 1; idx < 10; idx++)
		{
			final String stopID = stopIDPre + idx;
			try
			{
				URL url = new URL("http://185.201.144.208/kritzertrias/trias");
				URLConnection con = url.openConnection();
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setConnectTimeout(1000); // long timeout, but not infinite
				con.setReadTimeout(3000);
				con.setUseCaches(false);
				con.setDefaultUseCaches(false);
				con.setRequestProperty("Content-Type", "text/xml");
				OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
				writer.write(xmlCreator.getRequestXML(stopID));
				writer.flush();
				writer.close();

				// reading the response
				InputStreamReader reader = new InputStreamReader(con.getInputStream());
				BufferedReader br = new BufferedReader(reader);
				StringBuilder sbResponse = new StringBuilder();
				while (br.ready())
				{
					sbResponse.append(br.readLine());
				}
				br.close();

				Response.printAsync(sbResponse.toString());
			}
			catch (Throwable t)
			{
				t.printStackTrace(System.out);
				errors++;
			}
		}

		System.out.println("Errors: " + errors);
	}
}
