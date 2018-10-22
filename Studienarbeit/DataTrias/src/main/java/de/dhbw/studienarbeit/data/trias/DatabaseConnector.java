package de.dhbw.studienarbeit.data.trias;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseConnector
{
	private final static File file = new File("triasLogger.txt");
	
	public static void createFile() {
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static void write(String response)
	{
		try
		{
			FileWriter writer = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(response);
			bw.write(System.lineSeparator());
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
