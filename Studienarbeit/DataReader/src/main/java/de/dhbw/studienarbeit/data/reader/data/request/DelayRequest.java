package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public interface DelayRequest
{
	public void setWeekday(Optional<Weekday> weekday);

	public void setHour(Optional<Hour> hour);

	public void setLineID(Optional<LineID> lineID);

	public CountData getCancelledStops() throws IOException;

	public List<Delay> getDelays() throws IOException;
}
