package de.dhbw.studienarbeit.data.reader.data.request;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.OperatorName;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationData;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class RequestDBTest
{
	@Test
	public void sqlStationNameOnly() throws Exception
	{
		final RequestDB request = createDelayRequest();
		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringStationNameOnly() throws Exception
	{
		final String stationName = "stationName";
		final RequestDB request = createDelayRequest(stationName);

		assertThat(request.toString(), is(stationName));
	}

	@Test
	public void sqlStationNameAndWeekday() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterWeekdays(createWeekdayList(1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID"
				+ " AND WEEKDAY(timetabledTime) IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringStationNameAndWeekday() throws Exception
	{
		final RequestDB request = createDelayRequest("Main Station");
		request.filterWeekdays(Arrays.asList(Weekday.MONDAY));

		final String string = "Main Station (Montag)";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void sqlStationNameAndMultipleWeekdays() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterWeekdays(createWeekdayList(2));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID"
				+ " AND WEEKDAY(timetabledTime) IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringStationNameAndMultipleWeekday() throws Exception
	{
		final RequestDB request = createDelayRequest("Main Station");
		request.filterWeekdays(Arrays.asList(Weekday.MONDAY, Weekday.SATURDAY));

		final String string = "Main Station (Montag, Samstag)";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void sqlLineNames() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterLineNames(createLineNameList(1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND name IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringLineNames() throws Exception
	{
		final RequestDB request = createDelayRequest("Main Station");
		request.filterLineNames(Arrays.asList(new LineName("Bus 100")));

		final String string = "Main Station (Bus 100)";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void sqlMultipleLineNames() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterLineNames(createLineNameList(2));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND name IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringMultipleLineNames() throws Exception
	{
		final RequestDB request = createDelayRequest("Main Station");
		request.filterLineNames(Arrays.asList(new LineName("Bus 100"), new LineName("S-Bahn S1")));

		final String string = "Main Station (Bus 100, S-Bahn S1)";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void sqlLineNamesAndDestinations() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterLineNames(createLineNameList(2));
		request.filterLineDestination(createLineDestinationList(2));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND name IN (?, ?) AND destination IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringLineNamesAndDestinations() throws Exception
	{
		final RequestDB request = createDelayRequest("Main Station");
		request.filterLineNames(Arrays.asList(new LineName("Bus 100"), new LineName("S-Bahn S1")));
		request.filterLineDestination(Arrays.asList(new LineDestination("Bahnhof"), new LineDestination("Ziel2")));

		final String string = "Main Station (Bus 100, S-Bahn S1) -> (Bahnhof, Ziel2)";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void sqlCount() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterLineNames(createLineNameList(1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND name IN (?) AND realtime IS NULL GROUP BY delay;";

		assertThat(request.getCountCancelledSQL(), is(sql));
	}

	@Test
	public void setStartTimeWithoutEnd() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartHour(Hour.HOUR10);
	}

	@Test
	public void setEndTimeWithoutStart() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterEndHour(Hour.HOUR10);
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setStartThanEndAndEndtimeBeforeStarttime() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterEndHour(Hour.HOUR1);
		request.filterStartHour(Hour.HOUR10);
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setEndThanStartAndEndtimeBeforeStarttime() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartHour(Hour.HOUR10);
		request.filterEndHour(Hour.HOUR1);
	}

	@Test
	public void setValidStartAndEndTime() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartHour(Hour.HOUR1);
		request.filterEndHour(Hour.HOUR10);
	}

	@Test
	public void sqlWithStartTime() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartHour(Hour.HOUR1);

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND HOUR(timetabledTime) >= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringWithStartTime() throws Exception
	{
		final RequestDB request = createDelayRequest("Station");
		request.filterStartHour(Hour.HOUR1);

		final String string = "Station ab: 1:00 Uhr";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void sqlWithEndTime() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterEndHour(Hour.HOUR1);

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND HOUR(timetabledTime) <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringWithEndTime() throws Exception
	{
		final RequestDB request = createDelayRequest("Station");
		request.filterEndHour(Hour.HOUR1);

		final String string = "Station bis: 1:00 Uhr";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void sqlWithStartAndEndTime() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartHour(Hour.HOUR1);
		request.filterEndHour(Hour.HOUR10);

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND HOUR(timetabledTime) >= ? AND HOUR(timetabledTime) <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void stringWithStartAndEndTime() throws Exception
	{
		final RequestDB request = createDelayRequest("Station");
		request.filterStartHour(Hour.HOUR1);
		request.filterEndHour(Hour.HOUR10);

		final String string = "Station ab: 1:00 Uhr bis: 10:00 Uhr";

		assertThat(request.toString(), is(string));
	}

	@Test
	public void setStartTimeWithoutEndDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartDate(new Date());
	}

	@Test
	public void setEndTimeWithoutStartDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterEndDate(new Date());
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setStartThanEndAndEndtimeBeforeStarttimeDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartDate(new Date());
		request.filterEndDate(new Date(0));
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setEndThanStartAndEndtimeBeforeStarttimeDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterEndDate(new Date(0));
		request.filterStartDate(new Date());
	}

	@Test
	public void setStartAndEndTimeSameValueDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		final Date date = new Date();

		request.filterStartDate(date);
		request.filterEndDate(date);
	}

	@Test
	public void setValidStartAndEndTimeDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartDate(new Date());
		request.filterEndDate(new Date());
	}

	@Test
	public void sqlWithStartTimeDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartDate(new Date());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND timetabledTime >= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithEndTimeDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterEndDate(new Date());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND timetabledTime <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithStartAndEndTimeDate() throws Exception
	{
		final RequestDB request = createDelayRequest();
		request.filterStartDate(new Date());
		request.filterEndDate(new Date());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Line WHERE stationID = ? AND Stop.lineID = Line.lineID AND timetabledTime >= ? AND timetabledTime <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void possibleEndHoursWithNoStartHour() throws Exception
	{
		final Request request = createDelayRequest();

		final List<Hour> hours = request.getPossibleEndHours();

		assertThat(hours.size(), is(Hour.values().length));
	}

	@Test
	public void possibleStartHoursWithNoEndHour() throws Exception
	{
		final Request request = createDelayRequest();

		final List<Hour> hours = request.getPossibleStartHours();

		assertThat(hours.size(), is(Hour.values().length));
	}

	@Test
	public void possibleStartHoursWithEndHour() throws Exception
	{
		final Request request = createDelayRequest();
		request.filterEndHour(Hour.HOUR3);

		final List<Hour> hours = request.getPossibleStartHours();

		assertThat(hours.size(), is(2));
		assertTrue(hours.contains(Hour.HOUR1));
		assertTrue(hours.contains(Hour.HOUR2));
	}

	@Test
	public void possibleEndHoursWithStartHour() throws Exception
	{
		final Request request = createDelayRequest();
		request.filterStartHour(Hour.HOUR20);

		final List<Hour> hours = request.getPossibleEndHours();

		assertThat(hours.size(), is(4));
		assertTrue(hours.contains(Hour.HOUR21));
		assertTrue(hours.contains(Hour.HOUR22));
		assertTrue(hours.contains(Hour.HOUR23));
		assertTrue(hours.contains(Hour.HOUR24));
	}

	private RequestDB createDelayRequest()
	{
		return new RequestDB(new StationData(new StationID("id"), new StationName("myStationName"), new Position(0, 0),
				new OperatorName("foo")));
	}

	private RequestDB createDelayRequest(String stationName)
	{
		return new RequestDB(new StationData(new StationID("id"), new StationName(stationName), new Position(0, 0),
				new OperatorName("foo")));
	}

	private List<LineName> createLineNameList(int count)
	{
		final List<LineName> names = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			names.add(new LineName("myLineName"));
		}
		return names;
	}

	private List<LineDestination> createLineDestinationList(int count)
	{
		final List<LineDestination> destinations = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			destinations.add(new LineDestination("foo"));
		}
		return destinations;
	}

	private Collection<Weekday> createWeekdayList(int count)
	{
		final List<Weekday> weekdays = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			weekdays.add(Weekday.MONDAY);
		}
		return weekdays;
	}
}
