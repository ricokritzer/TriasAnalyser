package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;

public class App
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		final long requestCycle = 10; // in seconds
		final Coordinates karlsruhe = new Coordinates(49.01, 8.4); // Weather of Karlsruhe, DE

		while (true)
		{
			System.out.println(karlsruhe);
			Thread.sleep(requestCycle * 1000);
			karlsruhe.updateData();
		}
	}
}
