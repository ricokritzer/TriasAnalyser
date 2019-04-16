package de.dhbw.studienarbeit.web.data.vehicletype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.vehicletype.DelayVehicleTypeDB;
import de.dhbw.studienarbeit.data.reader.data.vehicletype.VehicleType;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayVehicleTypeWO extends Updateable
{
	private Delay<VehicleType> delayVehicleType = new DelayVehicleTypeDB();

	private List<DelayData<VehicleType>> data = new ArrayList<>();

	public DelayVehicleTypeWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<VehicleType>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayVehicleType.getDelays();
	}

	public void setDelayVehicleType(Delay<VehicleType> delayVehicleType)
	{
		this.delayVehicleType = delayVehicleType;
		update();
	}
}
