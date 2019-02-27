package de.dhbw.studienarbeit.web.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DelayVehicleTypeDB;

public class DelaysVehicleType extends Updateable
{
	private List<DelayVehicleTypeDB> data = new ArrayList<>();

	public DelaysVehicleType(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
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
