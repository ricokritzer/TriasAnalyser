package de.dhbw.studienarbeit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App
{
	public static void main(String[] args)
	{
		int errors = 0;

		final String stopIDPre = "de:8212:";

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

				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append(
						"<Trias version=\"1.1\" xmlns=\"http://www.vdv.de/trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
				sb.append("<ServiceRequest>");
				sb.append("<siri:RequestorRef>7qzuxfx8EPvH</siri:RequestorRef>");
				sb.append("<RequestPayload>");
				sb.append("<StopEventRequest>");
				sb.append("<Location>");
				sb.append("<LocationRef>");
				sb.append("<StopPointRef>" + stopID + "</StopPointRef>");
				sb.append("</LocationRef>");
				sb.append("<DepArrTime>" + currentTime() + "</DepArrTime>");
				sb.append("</Location>");
				sb.append("<Params>");
				sb.append("<NumberOfResults>10</NumberOfResults>");
				sb.append("<StopEventType>departure</StopEventType>");
				sb.append("<IncludeRealtimeData>true</IncludeRealtimeData>");
				sb.append("</Params>");
				sb.append("</StopEventRequest>");
				sb.append("</RequestPayload>");
				sb.append("</ServiceRequest>");
				sb.append("</Trias>");

				writer.write(sb.toString());
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

	private static String currentTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String currentDate = sdf.format(new Date());
		System.out.println(currentDate);
		return currentDate;
	}
}
