package de.dhbw.studienarbeit.data.reader.data.request;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class DelayRequestDBTest
{
	@Test
	public void sqlStationNameOnly() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationID("myStation"));
		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlStationNameAndWeekday() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationID("myStation"));
		request.setWeekday(Optional.of(Weekday.MONDAY));

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ?" + " AND WEEKDAY(timetabledTime) = ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlLineID() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setLineID(Optional.of(new LineID(9)));

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND lineID = ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}
	
	@Test
	public void sqlCount() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setLineID(Optional.of(new LineID(9)));

		final String sql = "SELECT count(*) AS total FROM Stop WHERE stationID = ? AND lineID = ? AND realtime IS NULL;";

		assertThat(request.getCancelledSQL(), is(sql));
	}

	@Test
	public void setStartTimeWithoutEnd() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR10));
	}

	@Test
	public void setEndTimeWithoutStart() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR10));
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setStartThanEndAndEndtimeBeforeStarttime() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR1));
		request.setHourStart(Optional.of(Hour.HOUR10));
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setEntThanStartAndEndtimeBeforeStarttime() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR10));
		request.setHourEnd(Optional.of(Hour.HOUR1));
	}

	@Test
	public void setStartAndEndTimeSameValue() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR10));
		request.setHourEnd(Optional.of(Hour.HOUR10));
	}

	@Test
	public void setValidStartAndEndTime() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR1));
		request.setHourEnd(Optional.of(Hour.HOUR10));
	}

	@Test
	public void sqlWithStartTime() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR1));

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND HOUR(timetabledTime) >= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithEndTime() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourEnd(Optional.of(Hour.HOUR1));

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND HOUR(timetabledTime) <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithStartAndEndTime() throws Exception
	{
		final DelayRequestDB request = createDelayRequest();
		request.setHourStart(Optional.of(Hour.HOUR1));
		request.setHourEnd(Optional.of(Hour.HOUR10));

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND HOUR(timetabledTime) >= ? AND HOUR(timetabledTime) <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	private DelayRequestDB createDelayRequest()
	{
		return new DelayRequestDB(new StationID("myStationName"));
	}
}
