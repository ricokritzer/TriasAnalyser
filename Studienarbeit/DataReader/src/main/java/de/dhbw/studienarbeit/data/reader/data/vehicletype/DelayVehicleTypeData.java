package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayVehicleTypeData extends DelayData
{
	private final String vehicleType;

	public DelayVehicleTypeData(DelayMaximum delayMaximum, DelayAverage delayAverage, String vehicleType)
	{
		super(delayMaximum, delayAverage);
		this.vehicleType = vehicleType;
	}

	public String getVehicleType()
	{
		return vehicleType;
	}
}
