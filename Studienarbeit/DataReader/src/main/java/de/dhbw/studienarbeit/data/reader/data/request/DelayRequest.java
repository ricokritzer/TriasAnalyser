package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;

public interface DelayRequest
{
	@Deprecated
	public void setLines(Collection<Line> lines);

	public void setLineNames(Collection<LineName> names);

	public void setLineDestinations(Collection<LineDestination> destination);

	public CountData getCancelledStops() throws IOException;

	public List<DelayCountData> getDelayCounts() throws IOException;
}
