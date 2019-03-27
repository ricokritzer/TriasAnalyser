package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;

public interface DelayRequest
{
	public void setLines(Collection<Line> lines);

	public void addLine(Line line);

	public void removeLine(Line line);

	public void clearLines();

	public CountData getCancelledStops() throws IOException;

	public List<DelayCountData> getDelayCounts() throws IOException;
}
