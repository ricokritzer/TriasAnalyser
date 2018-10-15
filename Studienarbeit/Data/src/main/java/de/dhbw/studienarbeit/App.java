package de.dhbw.studienarbeit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class App {
	public static void main(String[] args) {
		try {
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
			sb.append(
					"<Trias version=\"1.1\" xmlns=\"http://www.vdv.de/trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
			sb.append("<ServiceRequest>");
			sb.append("<siri:RequestorRef>7qzuxfx8EPvH</siri:RequestorRef>");
			sb.append("<RequestPayload>");
			sb.append("<StopEventRequest>");
			sb.append("<Location>");
			sb.append("<LocationRef>");
			sb.append("<StopPointRef>de:8212:7</StopPointRef>");
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
			while (br.ready()) {
				sbResponse.append(br.readLine());
			}
			br.close();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document doc = parser.parse(new InputSource(new StringReader(sbResponse.toString())));
			Element docElement = doc.getDocumentElement();
			for (int i = 0; i < docElement.getElementsByTagName("EstimatedTime").getLength(); i++) {
				System.out.println(docElement.getElementsByTagName("PublishedLineName").item(i).getNodeName() + ": "
						+ docElement.getElementsByTagName("PublishedLineName").item(i).getTextContent());
				System.out.println(docElement.getElementsByTagName("DestinationText").item(i).getNodeName() + ": "
						+ docElement.getElementsByTagName("DestinationText").item(i).getTextContent());
				System.out.println(docElement.getElementsByTagName("EstimatedTime").item(i).getNodeName() + ": "
						+ docElement.getElementsByTagName("EstimatedTime").item(i).getTextContent());
			}
		} catch (Throwable t) {
			t.printStackTrace(System.out);
		}
	}

	private static String currentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String currentDate = sdf.format(new Date());
		System.out.println(currentDate);
		return currentDate;
	}
}
