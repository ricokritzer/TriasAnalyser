package de.dhbw.studienarbeit.web.data.update;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Updateable
{
	public static final long SECONDS = 1000l;
	public static final long MINUTES = 60 * SECONDS;
	public static final long HOURS = 60 * MINUTES;
	public static final long DAYS = 24 * HOURS;

	private Date lastUpdate = new Date(0);

	protected void update()
	{
		try
		{
			updateData();
			lastUpdate = new Date();
			System.gc();
		}
		catch (IOException e)
		{
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Unable to update.", e);
		}
	}

	protected abstract void updateData() throws IOException;

	public Date getLastUpdated()
	{
		return lastUpdate;
	}
}
