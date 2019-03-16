package de.dhbw.studienarbeit.data.trias.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.datamanagement.ServerNotAvailableException;
import de.dhbw.studienarbeit.data.reader.data.api.ApiKeyDB;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;

public class BBBStationUtil
{
	public static void main(String[] args)
	{
		getAllStations();
	}

	private static void getAllStations()
	{
		List<String> stationIDs = getStationIDs();
		stationIDs.stream().map(new Function<String, StationSaveable>()
		{

			@Override
			public StationSaveable apply(String stationID)
			{
				HttpURLConnection con;
				try
				{
					con = createConnection();
					request(con, getXML(stationID));
					String responseXML = readResponse(con);
					return getStationDB(stationID, responseXML);
				}
				catch (IOException | ServerNotAvailableException | SAXException | ParserConfigurationException e)
				{
					e.printStackTrace();
					apply(stationID);
				}
				catch (NullPointerException e)
				{
					System.out.println("No station for " + stationID);
				}
				return null;
			}
		}).forEach(s -> {
			if (s != null)
			{
				DatabaseSaver.saveData(s);
				System.out.println(s.getName() + " gespeichert");
			}
		});
	}

	private static StationSaveable getStationDB(String stationID, String responseXML)
			throws SAXException, IOException, ParserConfigurationException, NullPointerException
	{
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = parser.parse(new InputSource(new StringReader(responseXML)));
		Element docElement = doc.getDocumentElement();
		String stationName = docElement.getElementsByTagName("StopPointName").item(0).getTextContent();
		return new StationSaveable(stationID, stationName.substring(0, stationName.length() - 2),
				Double.valueOf(docElement.getElementsByTagName("Latitude").item(0).getTextContent()),
				Double.valueOf(docElement.getElementsByTagName("Longitude").item(0).getTextContent()), "bbb", true);
	}

	private static List<String> getStationIDs()
	{
		BufferedReader br;
		List<String> ids = new ArrayList<>();
		try
		{
			br = new BufferedReader(new FileReader(new File("stops_bbb.txt")));
			while (br.ready())
			{
				ids.add(br.readLine());
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return ids;
	}

	private static String getXML(String stationID) throws IOException
	{
		return new StringBuilder()//
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")//
				.append("<Trias version=\"1.1\" xmlns=\"http://www.vdv.de/trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">")//
				.append("<ServiceRequest>")//
				.append("<RequestPayload>")//
				.append("<LocationInformationRequest>")//
				.append("<LocationRef>")//
				.append("<StopPointRef>" + stationID + "</StopPointRef>")//
				.append("</LocationRef>")//
				.append("</LocationInformationRequest>")//
				.append("</RequestPayload>")//
				.append("</ServiceRequest>")//
				.append("</Trias>").toString();
	}

	private static HttpURLConnection createConnection() throws IOException
	{
		final URL triasURL = new URL(new ApiKeyDB().getApiKeys(new OperatorID("bbb")).get(0).getUrl());
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

	private static void request(final URLConnection connection, final String request) throws IOException
	{
		final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(request);
		writer.flush();
		writer.close();
	}

	private static String readResponse(final HttpURLConnection connection)
			throws IOException, ServerNotAvailableException
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
}
