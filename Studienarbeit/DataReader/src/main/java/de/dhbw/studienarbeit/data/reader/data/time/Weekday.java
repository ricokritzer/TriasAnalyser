package de.dhbw.studienarbeit.data.reader.data.time;

public enum Weekday
{
	MONDAY("Montag"), //
	TUESDAY("Dienstag"), //
	WEDNESDAY("Mittwoch"), //
	THURSDAY("Donnerstag"), //
	FRIDAY("Freitag"), //
	SATURDAY("Samstag"), // ,
	SUNDAY("Sonntag");

	private String name;

	private Weekday(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
