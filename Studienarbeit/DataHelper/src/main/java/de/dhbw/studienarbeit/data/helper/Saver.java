package de.dhbw.studienarbeit.data.helper;

public class Saver
{
	private final TextSaver saver = new TextSaver("ausgabe.txt");

	public void save(final DataModel data)
	{
		saver.save(data.getSQLQuerry());
	}

	public void logError(Exception ex)
	{
		saver.logError(ex);
	}
}
