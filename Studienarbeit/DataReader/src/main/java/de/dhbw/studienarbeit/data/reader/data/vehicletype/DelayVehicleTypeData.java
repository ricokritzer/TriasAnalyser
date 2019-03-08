package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayVehicleTypeData extends DelayData
{
	private final String vehicleType;

	public DelayVehicleTypeData(double delayAverage, double delayMaximum, String vehicleType)
	{
		super(delayMaximum, delayAverage);
		this.vehicleType = vehicleType;
	}

	public String getVehicleType()
	{
		return vehicleType;
	}
}
