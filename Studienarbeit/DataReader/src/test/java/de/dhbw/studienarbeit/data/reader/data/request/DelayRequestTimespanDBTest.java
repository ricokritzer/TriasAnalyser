package de.dhbw.studienarbeit.data.reader.data.request;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class DelayRequestTimespanDBTest
{
	@Test
	public void sqlStationNameOnly() throws Exception
	{
		final DelayRequestTimespanDB request = new DelayRequestTimespanDB(new StationID("myStation"));
		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlStationNameAndWeekday() throws Exception
	{
		final DelayRequestTimespanDB request = new DelayRequestTimespanDB(new StationID("myStation"));
		request.addWeekday(Weekday.MONDAY);

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ?"
				+ " AND WEEKDAY(timetabledTime) IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlStationNameAndMultipleWeekdays() throws Exception
	{
		final DelayRequestTimespanDB request = new DelayRequestTimespanDB(new StationID("myStation"));
		request.addWeekday(Weekday.MONDAY);
		request.addWeekday(Weekday.WEDNESDAY);

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ?"
				+ " AND WEEKDAY(timetabledTime) IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlLineID() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.addLine(createLine());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND lineID IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlMultipleLineIDs() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.addLine(createLine());
		request.addLine(createLine());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND lineID IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlCount() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.addLine(createLine());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay FROM Stop WHERE stationID = ? AND lineID IN (?) AND realtime IS NULL GROUP BY delay;";

		assertThat(request.getCancelledSQL(), is(sql));
	}

	@Test
	public void setStartTimeWithoutEnd() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR10));
	}

	@Test
	public void setEndTimeWithoutStart() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR10));
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setStartThanEndAndEndtimeBeforeStarttime() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR1));
		request.setHourStart(Optional.of(Hour.HOUR10));
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setEndThanStartAndEndtimeBeforeStarttime() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR10));
		request.setHourEnd(Optional.of(Hour.HOUR1));
	}

	@Test
	public void setStartAndEndTimeSameValue() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR10));
		request.setHourEnd(Optional.of(Hour.HOUR10));
	}

	@Test
	public void setValidStartAndEndTime() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR1));
		request.setHourEnd(Optional.of(Hour.HOUR10));
	}

	@Test
	public void sqlWithStartTime() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND HOUR(timetabledTime) >= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithEndTime() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND HOUR(timetabledTime) <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithStartAndEndTime() throws Exception
	{
		final DelayRequestTimespanDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR1));
		request.setHourEnd(Optional.of(Hour.HOUR10));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND HOUR(timetabledTime) >= ? AND HOUR(timetabledTime) <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	private DelayRequestTimespanDB createDelayRequest()
	{
		return new DelayRequestTimespanDB(new StationID("myStationName"));
	}

	private Line createLine()
	{
		return new LineData(new LineID(1), new LineName("foo"), new LineDestination("bar"));
	}
}
