package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.DelayVehicleTypeDB;

public class DelaysVehicleType extends Updateable
{
	private List<DelayVehicleTypeDB> data = new ArrayList<>();

	public DelaysVehicleType()
	{
		DataUpdater.scheduleUpdate(this, 3, DataUpdater.HOURS);
	}

	public List<DelayVehicleTypeDB> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayVehicleTypeDB.getDelays();
	}
}
