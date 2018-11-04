package de.dhbw.studienarbeit.data.trias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class TriasXMLRequest
{
	private String url;
	private String key;
	private String stationID;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public TriasXMLRequest(ApiKey apiKey, Station station)
	{
		this.url = apiKey.getUrl();
		this.key = apiKey.getKey();
		this.stationID = station.getStationID();
	}

	/**
	 * 
	 * @return List of next stops that have real time data. Empty list, if no trains
	 *         with real time data arrive in the next 2 hours.
	 */
	public List<Stop> getResponse()
	{
		List<Stop> stops = new ArrayList<>();
		try
		{
			URLConnection con = createConnection();
			request(con, getXML());
			String responseXML = readResponse(con);
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			final Document doc = parser.parse(new InputSource(new StringReader(responseXML)));
			final Element docElement = doc.getDocumentElement();
			for (int i = 0; i < docElement.getElementsByTagName("TimetabledTime").getLength(); i++)
			{
				if (docElement.getElementsByTagName("EstimatedTime") == null)
				{
					continue;
				}
				String publishedLineName = docElement.getElementsByTagName("PublishedLineName").item(i)
						.getTextContent();
				String destinationText = docElement.getElementsByTagName("DestinationText").item(i).getTextContent();
				Date estimatedTime = sdf
						.parse(docElement.getElementsByTagName("EstimatedTime").item(i).getTextContent());
				Date timetabledTime = sdf
						.parse(docElement.getElementsByTagName("TimetabledTime").item(i).getTextContent());

				Line line = new Line(publishedLineName, destinationText);
				stops.add(new Stop(stationID, line, timetabledTime, estimatedTime));
			}
		}
		catch (IOException | SAXException | ParserConfigurationException | DOMException | ParseException e)
		{
			e.printStackTrace();
		}

		sortStops(stops);
		return stops;
	}

	/**
	 * sorts stops by estimated arrival time
	 * @param List of stops
	 */
	private void sortStops(List<Stop> stops)
	{
		stops.sort((stop1, stop2) -> {
			if (stop1.getRealTime().before(stop2.getRealTime()))
			{
				return -1;
			}
			return 1;
		});
	}

	private String readResponse(final URLConnection connection) throws IOException
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

	private void request(final URLConnection connection, final String request) throws IOException
	{
		final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(request);
		writer.flush();
		writer.close();
	}

	private URLConnection createConnection() throws IOException
	{
		final URL triasURL = new URL(url);
		final URLConnection con = triasURL.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(1000);
		con.setReadTimeout(3000);
		con.setUseCaches(false);
		con.setDefaultUseCaches(false);
		con.setRequestProperty("Content-Type", "text/xml");
		return con;
	}

	/**
	 * 
	 * @return XML request for TRIAS, for the given key and stationID
	 */
	private String getXML()
	{
		return new StringBuilder()//
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")//
				.append("<Trias version=\"1.1\" xmlns=\"http://www.vdv.de/trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")//
				.append("<ServiceRequest>")//
				.append("<siri:RequestorRef>" + this.key + "</siri:RequestorRef>")//
				.append("<RequestPayload>")//
				.append("<StopEventRequest>")//
				.append("<Location>")//
				.append("<LocationRef>")//
				.append("<StopPointRef>" + this.stationID + "</StopPointRef>")//
				.append("</LocationRef>")//
				.append("<DepArrTime>" + currentTime() + "</DepArrTime>")//
				.append("</Location>")//
				.append("<Params>")//
				.append("<NumberOfResults>10</NumberOfResults>")//
				.append("<StopEventType>departure</StopEventType>")
				.append("<IncludeRealtimeData>true</IncludeRealtimeData>")//
				.append("</Params>")//
				.append("</StopEventRequest>")//
				.append("</RequestPayload>")//
				.append("</ServiceRequest>")//
				.append("</Trias>").toString();
	}

	private String currentTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
	}
}