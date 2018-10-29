package de.dhbw.studienarbeit.data.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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

	private List<ApiKey> dataWeatherApiKeys = new ArrayList<>();
	private List<ApiKey> dataTriasApiKeys = new ArrayList<>();

	private static Settings data = new Settings();

	public static Settings getInstance()
	{
		return data;
	}

	public static void main(String[] args)
	{

	}

	private Settings()
	{
		try
		{
			LOGGER.log(Level.INFO, "Reading configuration data...");

			final ClassLoader classLoader = getClass().getClassLoader();
			final File file = new File(classLoader.getResource("Configuration.conf").getFile());
			final DocumentBuilder parser = factory.newDocumentBuilder();
			final Document doc = parser.parse(new InputSource(new BufferedReader(new FileReader(file))));
			final Element docElement = doc.getDocumentElement();

			final Element database = (Element) docElement.getElementsByTagName("database").item(0);

			databaseHostname = docElement.getElementsByTagName("hostname").item(0).getTextContent();
			databasePort = docElement.getElementsByTagName("port").item(0).getTextContent();
			databaseName = docElement.getElementsByTagName("name").item(0).getTextContent();

			final Element users = (Element) database.getElementsByTagName("users").item(0);

			final Element writer = (Element) users.getElementsByTagName("writer").item(0);
			databaseWriterUser = writer.getElementsByTagName("name").item(0).getTextContent();
			databaseWriterPassword = writer.getElementsByTagName("password").item(0).getTextContent();

			final Element reader = (Element) users.getElementsByTagName("reader").item(0);
			databaseReaderUser = reader.getElementsByTagName("name").item(0).getTextContent();
			databaseReaderPassword = reader.getElementsByTagName("password").item(0).getTextContent();

			final Element weather = (Element) docElement.getElementsByTagName("weather").item(0);

			final NodeList weatherApis = weather.getElementsByTagName("api");
			for (int i = 0; i < weatherApis.getLength(); i++)
			{
				final Element api = (Element) weatherApis.item(i);
				final String key = api.getElementsByTagName("api-key").item(0).getTextContent();
				final int requestLimit = Integer
						.parseInt(api.getElementsByTagName("request-limit").item(0).getTextContent());

				dataWeatherApiKeys.add(new ApiKey(key, requestLimit));
			}

			final Element trias = (Element) docElement.getElementsByTagName("trias").item(0);

			final NodeList triasApis = trias.getElementsByTagName("api");
			for (int i = 0; i < triasApis.getLength(); i++)
			{
				final Element api = (Element) triasApis.item(i);
				final String key = api.getElementsByTagName("api-key").item(0).getTextContent();
				final int requestLimit = Integer
						.parseInt(api.getElementsByTagName("request-limit").item(0).getTextContent());

				dataTriasApiKeys.add(new ApiKey(key, requestLimit));
			}

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

	public List<ApiKey> getDataWeatherApiKeys()
	{
		return dataWeatherApiKeys;
	}

	public List<ApiKey> getDataTriasApiKeys()
	{
		return dataTriasApiKeys;
	}
}
