package de.dhbw.studienarbeit.data.reader.data.request;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
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
	public void sqlLineName() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setLineNames(createLineNameList(1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND name IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlLineDestination() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setLineDestinations(createLineDestinationList(1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND destination IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlLineNameAndDestination() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setLineNames(createLineNameList(1));
		request.setLineDestinations(createLineDestinationList(1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND name IN (?) AND destination IN (?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlMultipleLineNames() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setLineNames(createLineNameList(2));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND name IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlMultipleLineDestinations() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setLineDestinations(createLineDestinationList(2));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND destination IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}
	
	@Test
	public void sqlLineNamesAndDestinations() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setLineNames(createLineNameList(2));
		request.setLineDestinations(createLineDestinationList(2));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ? AND name IN (?, ?) AND destination IN (?, ?) AND realtime IS NOT NULL GROUP BY delay;";

		assertThat(request.getDelaySQL(), is(sql));
	}

	@Test
	public void sqlCount() throws Exception
	{
		final DelayRequestFixedTimeDB request = createDelayRequest();
		request.setLineNames(createLineNameList(1));

		final String sql = "SELECT count(*) AS total, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay FROM Stop WHERE stationID = ? AND name IN (?) AND realtime IS NULL GROUP BY delay;";

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

	private DelayRequestFixedTimeDB createDelayRequest()
	{
		return new DelayRequestFixedTimeDB(new StationID("myStationName"));
	}

	private List<LineName> createLineNameList(int count)
	{
		final List<LineName> names = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			names.add(new LineName("foo"));
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
}
