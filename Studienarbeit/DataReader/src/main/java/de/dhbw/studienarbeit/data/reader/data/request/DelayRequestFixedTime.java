package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;

public interface DelayRequestFixedTime
{
	public void setTimestampStart(Optional<Date> timeStamp) throws InvalidTimeSpanException;

	public void setTimestampEnd(Optional<Date> timeStamp) throws InvalidTimeSpanException;

	public void addLine(Line line);

	public void removeLine(Line line);

	public void clearLines();

	public CountData getCancelledStops() throws IOException;

	public List<DelayCountData> getDelayCounts() throws IOException;
}
