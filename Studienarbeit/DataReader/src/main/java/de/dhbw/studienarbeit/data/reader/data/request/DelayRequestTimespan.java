package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public interface DelayRequestTimespan
{
	@Deprecated
	public void setWeekday(Optional<Weekday> weekday);

	public void addWeekday(Weekday weekday);

	public void removeWeekday(Weekday weekday);

	public void clearWeekdays();

	public void setHourStart(Optional<Hour> hour) throws InvalidTimeSpanException;

	public void setHourEnd(Optional<Hour> hour) throws InvalidTimeSpanException;

	@Deprecated
	public void setLineID(Optional<LineID> lineID);

	public void addLine(Line line);

	public void removeLine(Line line);

	public void clearLines();

	public CountData getCancelledStops() throws IOException;

	public List<DelayCountData> getDelayCounts() throws IOException;
}
