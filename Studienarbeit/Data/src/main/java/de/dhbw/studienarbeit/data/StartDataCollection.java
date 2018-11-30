package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartDataCollection
{

	private static final Logger LOGGER = Logger.getLogger(StartDataCollection.class.getName());

	public static void main(String[] args)
	{
		if (!App.isRunning())
		{
			try
			{
				if (args.length > 0)
				{
					App.startDataCollection(args[0]);
				}
				else
				{
					App.startDataCollection("");
				}
			}
			catch (IOException e)
			{
				LOGGER.log(Level.SEVERE, "Data collection could not be started", e);
			}
		}
	}

}
