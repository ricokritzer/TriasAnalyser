package de.dhbw.studienarbeit.data.repairer;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class RepairData implements DataModel
{
	private final String sql;

	public RepairData(String sql)
	{
		this.sql = sql;
	}

	@Override
	public String getSQLQuerry()
	{
		return sql;
	}

	@Override
	public void updateData(ApiKey apiKey) throws IOException
	{
		// there is nothing to update
	}

	@Override
	public Date nextUpdate()
	{
		// there is no next update
		return new Date();
	}
}
