package de.dhbw.studienarbeit.data.reader.data.request;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineID;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;

public class DelayRequestFixedTimeDBTest
{
	@Test
	public void sqlStationNameOnly() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlLine() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.addLine(createLine());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND lineID IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlMultipleLines() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.addLine(createLine());
		request.addLine(createLine());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND lineID IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlCount() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.addLine(createLine());

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay FROM Stop WHERE stationID = ? AND lineID IN (?) AND realtime IS NULL GROUP BY delay;";

		assertThat(request.getCancelledSQL(), is(sql));
	}

	@Test
	public void setStartTimeWithoutEnd() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampStart(Optional.of(new Date()));
	}

	@Test
	public void setEndTimeWithoutStart() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampEnd(Optional.of(new Date()));
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setStartThanEndAndEndtimeBeforeStarttime() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampStart(Optional.of(new Date()));
		request.setTimestampEnd(Optional.of(new Date(0)));
	}

	@Test(expected = InvalidTimeSpanException.class)
	public void setEndThanStartAndEndtimeBeforeStarttime() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampEnd(Optional.of(new Date(0)));
		request.setTimestampStart(Optional.of(new Date()));
	}

	@Test
	public void setStartAndEndTimeSameValue() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		final Date date = new Date();

		request.setTimestampStart(Optional.of(date));
		request.setTimestampEnd(Optional.of(date));
	}

	@Test
	public void setValidStartAndEndTime() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampStart(Optional.of(new Date()));
		request.setTimestampEnd(Optional.of(new Date()));
	}

	@Test
	public void sqlWithStartTime() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampStart(Optional.of(new Date()));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND timetabledTime >= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithEndTime() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampEnd(Optional.of(new Date()));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND timetabledTime <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlWithStartAndEndTime() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setTimestampStart(Optional.of(new Date()));
		request.setTimestampEnd(Optional.of(new Date()));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND timetabledTime >= ? AND timetabledTime <= ? AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void test() throws Exception
	{
		assertTrue(false);
	}

	private DelayRequestFixedTimeDB createDelayRequest()
	{
		return new DelayRequestFixedTimeDB(new StationID("myStationName"));
	}

	private Line createLine()
	{
		return new LineData(new LineID(1), new LineName("foo"), new LineDestination("bar"));
	}
}
