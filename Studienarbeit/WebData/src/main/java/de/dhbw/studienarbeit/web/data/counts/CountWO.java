package de.dhbw.studienarbeit.web.data.counts;

import java.util.Date;

import de.dhbw.studienarbeit.data.reader.data.count.Count;

public class CountWO
{
	private final Count value;
	private final Date lastUpdate;

	public CountWO(Count value, Date lastUpdate)
	{
		super();
		this.value = value;
		this.lastUpdate = lastUpdate;
	}

	public Count getValue()
	{
		return value;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
