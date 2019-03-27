package de.dhbw.studienarbeit.data.reader.data.request;

import java.util.Collection;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public interface DelayRequestTimespan extends DelayRequest
{
	public void setWeekdays(Collection<Weekday> weekday);

	public void addWeekday(Weekday weekday);

	public void removeWeekday(Weekday weekday);

	public void clearWeekdays();

	public void setHourStart(Optional<Hour> hour) throws InvalidTimeSpanException;

	public void setHourEnd(Optional<Hour> hour) throws InvalidTimeSpanException;
}
