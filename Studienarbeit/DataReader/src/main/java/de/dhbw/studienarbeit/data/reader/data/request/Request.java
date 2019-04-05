package de.dhbw.studienarbeit.data.reader.data.request;

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
	public CountData getCancelledStops();

	public List<DelayCountData> getDelays();

	public List<LineName> getPossibleLineNames();

	public List<LineDestination> getPossibleLineDestinations();

	public Request filterLineNames(Collection<LineName> lineNames);

	public Request filterLineDestination(Collection<LineDestination> lineDestinations);

	public Request filterWeekdays(Collection<Weekday> weekdays);

	public Request filterStartDate(Date start);

	public Request filterEndDate(Date end);

	public Request filterStartHour(Hour hour);

	public Request filterEndHour(Hour hour);
}
