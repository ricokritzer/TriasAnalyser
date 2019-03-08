package de.dhbw.studienarbeit.data.reader.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class SettingsReadOnly
{
	private static final Logger LOGGER = Logger.getLogger(SettingsReadOnly.class.getName());
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	private String databaseHostname;
	private String databasePort;
	private String databaseName;
	private String databaseReaderUser;
	private String databaseReaderPassword;

	private static SettingsReadOnly data = new SettingsReadOnly();

	public static SettingsReadOnly getInstance()
	{
		return data;
	}

	private SettingsReadOnly()
	{
		try
		{
			LOGGER.log(Level.INFO, "Reading configuration data...");

			final File file = new File(System.getProperty("user.home") + File.separator + "Studienarbeit"
					+ File.separator + "Configuration.conf");
			final DocumentBuilder parser = factory.newDocumentBuilder();
			final Document doc = parser.parse(new InputSource(new BufferedReader(new FileReader(file))));
			final Element docElement = doc.getDocumentElement();

			final Element database = (Element) docElement.getElementsByTagName("database").item(0);

			databaseHostname = docElement.getElementsByTagName("hostname").item(0).getTextContent();
			databasePort = docElement.getElementsByTagName("port").item(0).getTextContent();
			databaseName = docElement.getElementsByTagName("name").item(0).getTextContent();

			final Element users = (Element) database.getElementsByTagName("users").item(0);

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

	public String getDatabaseReaderUser()
	{
		return databaseReaderUser;
	}

	public String getDatabaseReaderPassword()
	{
		return databaseReaderPassword;
	}
}
