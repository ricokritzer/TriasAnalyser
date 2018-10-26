package de.dhbw.studienarbeit.data.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;

public class Settings
{
	private static final Logger LOGGER = Logger.getLogger(Settings.class.getName());
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	private String databaseHostname;
	private String databasePort;
	private String databaseName;
	private String databaseWriterUser;
	private String databaseWriterPassword;
	private String databaseReaderUser;
	private String databaseReaderPassword;
	private String databaseManipulatorUser;
	private String databaseManipulatorPassword;

	private String dataWeatherApiKey;

	private static Settings data = new Settings();

	public static Settings getInstance()
	{
		return data;
	}

	private Settings()
	{
		try
		{
			LOGGER.log(Level.INFO, "Reading configuration data...");
			final DocumentBuilder parser = factory.newDocumentBuilder();
			final Document doc = parser
					.parse(new InputSource(new BufferedReader(new FileReader("Configuration.conf"))));
			final Element docElement = doc.getDocumentElement();

			databaseHostname = docElement.getElementsByTagName("hostname").item(0).getTextContent();
			databasePort = docElement.getElementsByTagName("port").item(0).getTextContent();
			databaseName = docElement.getElementsByTagName("name").item(0).getTextContent();

			final NamedNodeMap writer = docElement.getElementsByTagName("writer").item(0).getAttributes();
			databaseWriterUser = writer.getNamedItem("name").getTextContent();
			databaseWriterPassword = writer.getNamedItem("password").getTextContent();

			final NamedNodeMap reader = docElement.getElementsByTagName("reader").item(0).getAttributes();
			databaseReaderUser = reader.getNamedItem("name").getTextContent();
			databaseReaderPassword = reader.getNamedItem("password").getTextContent();

			final NamedNodeMap weather = docElement.getElementsByTagName("weather").item(0).getAttributes();
			dataWeatherApiKey = weather.getNamedItem("api-key").getTextContent();

			LOGGER.log(Level.INFO, "Reading configuration data completed successfully.");
		}
		catch (Exception ex)
		{
			LOGGER.log(Level.WARNING, "Reading configuration data does not succeed.", ex);
		}
	}

	public String getDatabaseHostname()
	{
		return databaseHostname;
	}

	public String getDatabasePort()
	{
		return databasePort;
	}

	public String getDatabaseName()
	{
		return databaseName;
	}

	public String getDatabaseWriterUser()
	{
		return databaseWriterUser;
	}

	public String getDatabaseWriterPassword()
	{
		return databaseWriterPassword;
	}

	public String getDatabaseReaderUser()
	{
		return databaseReaderUser;
	}

	public String getDatabaseReaderPassword()
	{
		return databaseReaderPassword;
	}

	public String getDatabaseManipulatorUser()
	{
		return databaseManipulatorUser;
	}

	public String getDatabaseManipulatorPassword()
	{
		return databaseManipulatorPassword;
	}

	public String getDataWeatherApiKey()
	{
		return dataWeatherApiKey;
	}
}
