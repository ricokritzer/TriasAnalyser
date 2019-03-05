package de.dhbw.studienarbeit.web.data.vehicletype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayVehicleTypeData;
import de.dhbw.studienarbeit.data.reader.database.DelayVehicleTypeDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayVehicleTypeWO extends Updateable
{
	private List<DelayVehicleTypeDB> data = new ArrayList<>();

	public DelayVehicleTypeWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayVehicleTypeData> getData()
	{
		return new ArrayList<>(data);
	}

	@Deprecated
	public List<DelayVehicleTypeDB> getList()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = DelayVehicleTypeDB.getDelays();
	}
}
