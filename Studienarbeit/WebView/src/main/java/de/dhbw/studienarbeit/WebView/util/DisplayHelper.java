package de.dhbw.studienarbeit.WebView.util;

public class DisplayHelper
{
	public static String delayToString(double delay)
	{
		int hour = 0;
		int min = 0;
		
		while (delay >= 3600)
		{
			hour++;
			delay -= 3600;
		}
		while (delay >= 60)
		{
			min++;
			delay -= 60;
		}
		
		if (hour == 0)
		{
			if (min == 0)
			{
				return Math.round(delay * 100) / 100d + "s";
			}
			return min + "m " + Math.round(delay) + "s";
		}
		return hour + "h " + min + "m " + Math.round(delay) + "s";
	}
}
