package de.dhbw.studienarbeit.data.weather;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		final long requestCycle = 60; // in seconds
		final Coordinates karlsruhe = new Coordinates(49.01, 8.4); // Weather of Karlsruhe, DE
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

		while (true)
		{
			try (BufferedWriter bw = new BufferedWriter(new FileWriter("ausgabe.txt", true)))
			{
				final Date date = new Date();
				bw.write(format.format(date) + "\t" + karlsruhe.toString());
				bw.newLine();
			}
			catch (IOException ex)
			{
				// ignore
			}
			Thread.sleep(requestCycle * 1000);
			karlsruhe.updateData();
		}
	}
}
