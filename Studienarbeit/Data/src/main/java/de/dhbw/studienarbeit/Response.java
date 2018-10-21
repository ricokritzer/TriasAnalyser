package de.dhbw.studienarbeit;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Response
{
	private final List<Stop> stops = new ArrayList<>();

	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	public Response(String text) throws SAXException, IOException, ParserConfigurationException
	{
		final DocumentBuilder parser = factory.newDocumentBuilder();
		final Document doc = parser.parse(new InputSource(new StringReader(text)));
		final Element docElement = doc.getDocumentElement();
		for (int i = 0; i < docElement.getElementsByTagName("EstimatedTime").getLength(); i++)
		{
			final String publishedLineName = docElement.getElementsByTagName("PublishedLineName").item(i)
					.getTextContent();
			final String destinationText = docElement.getElementsByTagName("DestinationText").item(i).getTextContent();
			final String estimatedTime = docElement.getElementsByTagName("EstimatedTime").item(i).getTextContent();
			final String timetabledTime = docElement.getElementsByTagName("TimetabledTime").item(i).getTextContent();

			stops.add(new Stop(timetabledTime, estimatedTime, publishedLineName, destinationText));
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		stops.forEach(s -> sb.append(s + System.lineSeparator()));
		return sb.toString();
	}
}
