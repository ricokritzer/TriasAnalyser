package de.dhbw.studienarbeit;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Response
{
	private String estimatedTime = "";
	private String publishedLineName = "";
	private String destinationText = "";

	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	public Response(String text) throws SAXException, IOException, ParserConfigurationException
	{
		final DocumentBuilder parser = factory.newDocumentBuilder();
		final Document doc = parser.parse(new InputSource(new StringReader(text)));
		final Element docElement = doc.getDocumentElement();
		for (int i = 0; i < docElement.getElementsByTagName("EstimatedTime").getLength(); i++)
		{
			publishedLineName = docElement.getElementsByTagName("PublishedLineName").item(i).getTextContent();
			destinationText = docElement.getElementsByTagName("DestinationText").item(i).getTextContent();
			estimatedTime = docElement.getElementsByTagName("EstimatedTime").item(i).getTextContent();
		}
	}

	public String toString()
	{
		return publishedLineName + "\t\t" + destinationText + "\t\t" + estimatedTime;
	}

	public static void printAsync(String text)
	{
		Thread t = new Thread(() -> {
			try
			{
				Response r = new Response(text);
				System.out.println(r.toString());
			}
			catch (SAXException | IOException | ParserConfigurationException e)
			{
				// ignore
			}
		});
		t.start();
	}
}
