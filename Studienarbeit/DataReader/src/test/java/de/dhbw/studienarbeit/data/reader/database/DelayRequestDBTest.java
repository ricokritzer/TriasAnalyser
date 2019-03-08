package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
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
				+ "FROM Stop WHERE stationID = ?";

		assertThat(request.getSQL("(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay"), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndWeekday() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationID("myStation"));
		request.setWeekday(Weekday.MONDAY);

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ?" + " AND DAYOFWEEK(timetabledTime) = ?";

		assertThat(request.getSQL("(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay"), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndWeekdayAndHour() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationID("myStationName"));
		request.setWeekday(Weekday.MONDAY);
		request.setHour(Hour.BETWEEN_00_AND_01);

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ?" + " AND DAYOFWEEK(timetabledTime) = ? AND HOUR(timetabledTime) = ?";

		assertThat(request.getSQL("(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay"), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndHour() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationID("myStationName"));
		request.setHour(Hour.BETWEEN_00_AND_01);

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ?" + " AND HOUR(timetabledTime) = ?";

		assertThat(request.getSQL("(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay"), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndHourAndLineID() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationID("myStationName"));
		request.setHour(Hour.BETWEEN_00_AND_01);
		request.setLineID(new LineID(9));

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop WHERE stationID = ?" + " AND HOUR(timetabledTime) = ? AND lineID = ?";

		assertThat(request.getSQL("(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay"), Is.is(sql));
	}
}
