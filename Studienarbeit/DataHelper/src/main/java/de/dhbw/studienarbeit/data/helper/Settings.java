package de.dhbw.studienarbeit.data.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import de.dhbw.studienarbeit.data.helper.database.DatabaseReader;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

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

	public List<ApiKey> getApiKeys(final String name) throws IOException
	{
		try
		{
			final DatabaseReader reader = new DatabaseReader();
			final List<ApiKey> keys = reader.readApiKeys(name);
			reader.disconnect();
			return keys;
		}
		catch (SQLException e)
		{
			throw new IOException("Unable to load API keys.", e);
		}
	}
}