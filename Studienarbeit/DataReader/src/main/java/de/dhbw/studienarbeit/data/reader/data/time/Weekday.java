package de.dhbw.studienarbeit.data.reader.data.time;

import java.util.Arrays;

public enum Weekday
{
	
	// Bitte in der Reihenfolge lassen, weil die compareTo-Methode die Reihenfolge verwendet
	
	MONDAY("Montag"), //
	TUESDAY("Dienstag"), //
	WEDNESDAY("Mittwoch"), //
	THURSDAY("Donnerstag"), //
	FRIDAY("Freitag"), //
	SATURDAY("Samstag"), // ,
	SUNDAY("Sonntag");

	private final String name;

	private Weekday(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public int getIdx()
	{
		return Arrays.asList(Weekday.values()).indexOf(this);
	}
}
