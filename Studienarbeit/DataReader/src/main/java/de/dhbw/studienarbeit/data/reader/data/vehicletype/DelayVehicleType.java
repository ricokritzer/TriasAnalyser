package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import java.io.IOException;
import java.util.List;

public interface DelayVehicleType
{
	List<DelayVehicleTypeData> getDelays() throws IOException;
}
