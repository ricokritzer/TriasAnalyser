package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;

import de.dhbw.studienarbeit.data.helper.Saver;

public class App
{
	public static void main(String[] args)
	{
		final long requestCycle = 60; // in seconds
		final Coordinates karlsruhe = new Coordinates(49.01, 8.4); // Weather of Karlsruhe, DE
		final Saver saver = new Saver();

		boolean run = true;

		while (run)
		{
			try
			{
				saver.save(karlsruhe.getLon(), karlsruhe.getLat(), karlsruhe.getTemp(), karlsruhe.getHumitdity(),
						karlsruhe.getPressure(), karlsruhe.getWind(), karlsruhe.getClouds());
				Thread.sleep(requestCycle * 1000);
				karlsruhe.updateData();
			}
			catch (InterruptedException e)
			{
				run = false;
				saver.logError(e);
			}
			catch (IOException e)
			{
				saver.logError(e);
			}
		}
	}
}
