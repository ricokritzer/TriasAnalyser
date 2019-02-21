package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Updateable
{
	private Date lastUpdate = new Date(0);

	protected void update()
	{
		try
		{
			updateData();
			lastUpdate = new Date();
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
