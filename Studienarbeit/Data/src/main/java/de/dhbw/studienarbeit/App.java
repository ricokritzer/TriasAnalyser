package de.dhbw.studienarbeit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		try
		{
			URL url = new URL("http://185.201.144.208/kritzertrias/trias");
			URLConnection con = url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(20000); // long timeout, but not infinite
			con.setReadTimeout(20000);
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			con.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<Trias version=\"1.1\" xmlns=\"http://www.vdv.de/trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
			sb.append("<ServiceRequest>");
			sb.append("<siri:RequestorRef>7qzuxfx8EPvH</siri:RequestorRef>");
			sb.append("<RequestPayload>");
			sb.append("<StopEventRequest>");
			sb.append("<Location>");
			sb.append("<LocationRef>");
			sb.append("<StopPointRef>de:8212:7</StopPointRef>");
			sb.append("</LocationRef>");
			sb.append("<DepArrTime>2018-10-15T9:50:00</DepArrTime>");
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
			System.out.println(sbResponse.toString());
			br.close();
		}
		catch (Throwable t)
		{
			t.printStackTrace(System.out);
		}
	}
}
