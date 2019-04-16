package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class DelayVehicleTypeData extends DelayData<VehicleType>
{
	public DelayVehicleTypeData(DelayMaximum delayMaximum, DelayAverage delayAverage, CountData count,
			VehicleType vehicleType)
	{
		super(delayMaximum, delayAverage, count, vehicleType);
	}

	@Override
	public String getValueString()
	{
		return value.toString();
	}
}
