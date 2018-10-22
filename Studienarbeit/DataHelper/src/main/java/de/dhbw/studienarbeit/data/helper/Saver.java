package de.dhbw.studienarbeit.data.helper;

public class Saver
{
	private final TextSaver saver = new TextSaver("ausgabe.txt");

	public void save(final DataModel data)
	{
		saver.save(data.getSQLQuerry());
	}

	@Deprecated
	public void save(final double lon, final double lat, final double temp, double humitidity, double pressure,
			double wind, double clouds)
	{
		saver.save(lon, lat, temp, humitidity, pressure, wind, clouds);
	}

	public void logError(Exception ex)
	{
		saver.logError(ex);
	}
}
