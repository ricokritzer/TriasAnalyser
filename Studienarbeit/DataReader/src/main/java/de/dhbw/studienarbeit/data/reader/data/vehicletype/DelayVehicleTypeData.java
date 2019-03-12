package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;

public class DelayVehicleTypeData extends DelayData<String>
{
	public DelayVehicleTypeData(DelayMaximum delayMaximum, DelayAverage delayAverage, String vehicleType)
	{
		super(delayMaximum, delayAverage, vehicleType);
	}

	@Deprecated
	public String getVehicleType()
	{
		return value;
	}
}
