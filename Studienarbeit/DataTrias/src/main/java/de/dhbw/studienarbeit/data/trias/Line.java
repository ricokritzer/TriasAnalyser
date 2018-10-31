package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class Line implements DataModel
{
	public final static String NAME = "name";
	
	@Override
	public String getSQLQuerry()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateData(ApiKey apiKey) throws IOException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date nextUpdate()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
