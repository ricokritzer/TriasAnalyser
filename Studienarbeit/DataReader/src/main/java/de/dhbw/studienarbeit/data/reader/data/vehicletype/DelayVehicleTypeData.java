package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class DelayVehicleTypeData implements DelayData
{
	private final double maximum;
	private final double average;
	private final String vehicleType;

	public DelayVehicleTypeData(double delayAverage, double delayMaximum, String vehicleType)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.vehicleType = vehicleType;
	}

	public String getVehicleType()
	{
		return vehicleType;
	}

	@Override
	public double getDelayMaximum()
	{
		return maximum;
	}

	@Override
	public double getDelayAverage()
	{
		return average;
	}
}
