package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public interface Request
{
	public CountData getCancelledStops() throws IOException;

	public List<DelayCountData> getDelays() throws IOException;

	public List<LineName> getPossibleLineNames() throws IOException;

	public List<LineDestination> getPossibleLineDestinations() throws IOException;

	public Request filterLineNames(Collection<LineName> lineNames);

	public Request filterLineDestination(Collection<LineDestination> lineDestinations);

	public Request filterWeekdays(Collection<Weekday> weekdays);

	public Request filterStartDate(Date start) throws InvalidTimeSpanException;

	public Request filterEndDate(Date end) throws InvalidTimeSpanException;

	public Request filterStartHour(Hour hour) throws InvalidTimeSpanException;

	public Request filterEndHour(Hour hour) throws InvalidTimeSpanException;

	@Override
	public String toString();
}
