package de.dhbw.studienarbeit.data.reader.data.request;

import java.util.Date;
import java.util.Optional;

public interface DelayRequestFixedTime extends DelayRequest
{
	public void setTimestampStart(Optional<Date> timeStamp) throws InvalidTimeSpanException;

	public void setTimestampEnd(Optional<Date> timeStamp) throws InvalidTimeSpanException;
}
