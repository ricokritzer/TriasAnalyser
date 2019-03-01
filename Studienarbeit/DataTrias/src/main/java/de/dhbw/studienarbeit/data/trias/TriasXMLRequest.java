package de.dhbw.studienarbeit.data.trias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.ServerNotAvailableException;
import edu.emory.mathcs.backport.java.util.Collections;

public class TriasXMLRequest
{
	private String url;
	private String key;
	private String stationID;

	private static final String formatString = "yyyy-MM-dd'T'HH:mm:ss";

	private static final Logger LOGGER = Logger.getLogger(TriasXMLRequest.class.getName());

	public TriasXMLRequest(ApiKey apiKey, Station station)
	{
		this.url = apiKey.getUrl();
		this.key = apiKey.getKey();
		this.stationID = station.getStationID();
	}

	/**
	 * @return List of next stops.
	 * @throws IOException
	 * @throws ServerNotAvailableException 
	 */
	public List<Stop> getResponse() throws IOException, ServerNotAvailableException
	{
		List<Stop> stops = new ArrayList<>();
		HttpURLConnection con = createConnection();
		request(con, getXML());
		LOGGER.log(Level.FINEST, getXML());
		String responseXML = readResponse(con);
		if (responseXML.isEmpty())
		{
			LOGGER.log(Level.FINEST, "Empty response.");
			return new ArrayList<>();
		}
		LOGGER.log(Level.FINEST, responseXML);
		DocumentBuilder parser;
		try
		{
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			final Document doc = parser.parse(new InputSource(new StringReader(responseXML)));
			final Element docElement = doc.getDocumentElement();
			Map<String, Situation> situations = new HashMap<>();
			for (int i = 0; i < docElement.getElementsByTagName("PtSituation").getLength(); i++)
			{
				Element ptSituation = (Element) docElement.getElementsByTagName("PtSituation").item(i);
				String id = ptSituation.getElementsByTagName("SituationNumber").item(0).getTextContent();
				int version = Integer.valueOf(ptSituation.getElementsByTagName("Version").item(0).getTextContent());
				String summary = ptSituation.getElementsByTagName("Summary").item(0).getTextContent();
				situations.put(id, new Situation(id, version, summary));
			}
			for (int i = 0; i < docElement.getElementsByTagName("RoadSituation").getLength(); i++)
			{
				LOGGER.log(Level.INFO, "RoadSituation: " + responseXML);
				Element ptSituation = (Element) docElement.getElementsByTagName("RoadSituation").item(i);
				String id = ptSituation.getElementsByTagName("SituationNumber").item(0).getTextContent();
				int version = Integer.valueOf(ptSituation.getElementsByTagName("Version").item(0).getTextContent());
				String summary = ptSituation.getElementsByTagName("Summary").item(0).getTextContent();
				situations.put(id, new Situation(id, version, summary));
			}
			for (int i = 0; i < docElement.getElementsByTagName("StopEvent").getLength(); i++)
			{
				Element stopEvent = (Element) docElement.getElementsByTagName("StopEvent").item(i);
				stops.add(new Stop(stationID, getPublishedLineName(stopEvent), getDestinationText(stopEvent),
						getTimetabledTime(stopEvent), getEstimatedTime(stopEvent), getSituations(stopEvent, situations)));
			}
		}
		catch (ParserConfigurationException | DOMException | ParseException | SAXException e)
		{
			LOGGER.log(Level.FINE, "Exception when parsing XML Response from TRIAS");
		}
		Collections.sort(stops);
		return stops;
	}

	private Situation[] getSituations(Element stopEvent, Map<String, Situation> situations)
	{
		Situation[] situationsForStop = new Situation[stopEvent.getElementsByTagName("SituationNumber").getLength()];
		for (int i = 0; i < stopEvent.getElementsByTagName("SituationNumber").getLength(); i++)
		{
			situationsForStop[i] =  situations.get(stopEvent.getElementsByTagName("SituationNumber").item(i).getTextContent());
		}
		return situationsForStop;
	}

	private Date getTimetabledTime(final Element stopEvent) throws ParseException
	{
		Date timetabledTime = new Date(new SimpleDateFormat(formatString)
				.parse(stopEvent.getElementsByTagName("TimetabledTime").item(0).getTextContent()).getTime());
		return timetabledTime;
	}

	private Optional<Date> getEstimatedTime(final Element stopEvent) throws ParseException
	{
		if (stopIsCancelled(stopEvent))
		{
			return Optional.empty();
		}
		if (stopEvent.getElementsByTagName("EstimatedTime").item(0) == null)
		{
			return Optional.ofNullable(new Date(0));
		}
		Date estimatedTime = new Date(new SimpleDateFormat(formatString)
				.parse(stopEvent.getElementsByTagName("EstimatedTime").item(0).getTextContent()).getTime());
		return Optional.ofNullable(estimatedTime);
	}

	private Boolean stopIsCancelled(final Element stopEvent)
	{
		return stopEvent.getElementsByTagName("Cancelled").item(0) != null
				&& Boolean.valueOf(stopEvent.getElementsByTagName("Cancelled").item(0).getTextContent());
	}

	private String getDestinationText(final Element stopEvent)
	{
		String destinationText = stopEvent.getElementsByTagName("DestinationText").item(0).getTextContent();
		destinationText = destinationText.substring(0, destinationText.length() - 2);
		return destinationText;
	}

	private String getPublishedLineName(Element stopEvent)
	{
		String publishedLineName = stopEvent.getElementsByTagName("PublishedLineName").item(0).getTextContent();
		return publishedLineName.substring(0, publishedLineName.length() - 2);
	}

	private String readResponse(final HttpURLConnection connection) throws IOException, ServerNotAvailableException
	{
		if (connection.getResponseCode() == 503)
		{
			throw new ServerNotAvailableException("Trias server response 503");
		}
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

	private HttpURLConnection createConnection() throws IOException
	{
		final URL triasURL = new URL(url);
		final HttpURLConnection con = (HttpURLConnection) triasURL.openConnection();
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
				.append("<StopEventType>departure</StopEventType>")//
				.append("<IncludePreviousCalls>false</IncludePreviousCalls>")//
				.append("<IncludeOnwardCalls>false</IncludeOnwardCalls>")//
				.append("<IncludeRealtimeData>true</IncludeRealtimeData>")//
				.append("<IncludeOperatingDays>false</IncludeOperatingDays>")//
				.append("</Params>")//
				.append("</StopEventRequest>")//
				.append("</RequestPayload>")//
				.append("</ServiceRequest>")//
				.append("</Trias>").toString();
	}

	private String currentTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new java.util.Date());
	}
}
