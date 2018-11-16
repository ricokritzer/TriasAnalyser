package de.dhbw.studienarbeit.data.trias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableLine;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
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
	 * 
	 * @return List of next stops that have real time data. Empty list, if no trains
	 *         with real time data arrive in the next 2 hours.
	 * @throws IOException
	 */
	public List<Stop> getResponse() throws IOException
	{
		List<Stop> stops = new ArrayList<>();
		URLConnection con = createConnection();
		request(con, getXML());
		LOGGER.log(Level.FINEST, getXML());
		String responseXML = readResponse(con);
		LOGGER.log(Level.FINEST, responseXML);
		DocumentBuilder parser;
		try
		{
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			final Document doc = parser.parse(new InputSource(new StringReader(responseXML)));
			final Element docElement = doc.getDocumentElement();
			for (int i = 0; i < docElement.getElementsByTagName("TimetabledTime").getLength(); i++)
			{
				stops.add(new Stop(stationID, getLine(docElement, i), getTimetabledTime(docElement, i),
						getEstimatedTime(docElement, i)));
			}
		}
		catch (ParserConfigurationException | DOMException | ParseException | SAXException e)
		{
			e.printStackTrace();
		}
		Collections.sort(stops);
		return stops;
	}

	private Date getTimetabledTime(final Element docElement, int i) throws ParseException
	{
		Date timetabledTime = new Date(new SimpleDateFormat(formatString)
				.parse(docElement.getElementsByTagName("TimetabledTime").item(i).getTextContent()).getTime());
		return timetabledTime;
	}

	private Optional<Date> getEstimatedTime(final Element docElement, int i) throws ParseException
	{
		if (stopIsCancelled(docElement, i))
		{
			return Optional.empty();
		}
		if (docElement.getElementsByTagName("EstimatedTime").item(i) == null)
		{
			return Optional.ofNullable(new Date(0));
		}
		Date estimatedTime = new Date(new SimpleDateFormat(formatString)
				.parse(docElement.getElementsByTagName("EstimatedTime").item(i).getTextContent()).getTime());
		return Optional.ofNullable(estimatedTime);
	}

	private Boolean stopIsCancelled(final Element docElement, int i)
	{
		return Boolean.valueOf(docElement.getElementsByTagName("Cancelled").item(i) != null
				&& Boolean.valueOf(docElement.getElementsByTagName("Cancelled").item(i).getTextContent()));
	}

	private String getDestinationText(final Element docElement, int i)
	{
		String destinationText = docElement.getElementsByTagName("DestinationText").item(i).getTextContent();
		destinationText = destinationText.substring(0, destinationText.length() - 2);
		return destinationText;
	}

	private Line getLine(Element docElement, int i) throws IOException
	{
		final String name = getPublishedLineName(docElement, i);
		final String destination = getDestinationText(docElement, i);
		final Optional<Integer> lineID = new DatabaseTableLine().getLineID(name, destination);
		if (lineID.isPresent())
		{
			return new Line(lineID.get(), name, destination);
		}

		saveLine(name, destination);

		final Optional<Integer> lineIDDatabase = new DatabaseTableLine().getLineID(name, destination);
		return new Line(lineIDDatabase.orElse(-1), name, destination);
	}

	private void saveLine(String name, String destination) throws IOException
	{
		new DatabaseSaver().save(new Line(name, destination));
	}

	private String getPublishedLineName(Element docElement, int i)
	{
		String publishedLineName = docElement.getElementsByTagName("PublishedLineName").item(i).getTextContent();
		return publishedLineName.substring(0, publishedLineName.length() - 2);
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
				.append("<StopEventType>departure</StopEventType>")//
				.append("<IncludeOnwardCalls>false</IncludeOnwardCalls>")//
				.append("<IncludeOperatingDays>false</IncludeOperatingDays>")//
				.append("<IncludeRealtimeData>true</IncludeRealtimeData>")//
				.append("<IncludeRealtimeData>true</IncludeRealtimeData>")//
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
