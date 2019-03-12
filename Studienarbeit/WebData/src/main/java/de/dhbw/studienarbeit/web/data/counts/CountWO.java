package de.dhbw.studienarbeit.web.data.counts;

import java.util.Date;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CountWO
{
	private final CountData value;
	private final Date lastUpdate;

	public CountWO(CountData value, Date lastUpdate)
	{
		super();
		this.value = value;
		this.lastUpdate = lastUpdate;
	}

	public CountData getValue()
	{
		return value;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}
}
