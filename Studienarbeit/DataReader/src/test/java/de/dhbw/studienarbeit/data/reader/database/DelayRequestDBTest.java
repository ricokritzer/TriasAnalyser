package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class DelayRequestDBTest
{
	@Test
	public void sqlStationNameOnly() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationName("myStationName"));
		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Station WHERE Station.stationID = Stop.stationID AND Station.name = ?";

		assertThat(request.getSQL(), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndWeekday() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationName("myStationName"));
		request.setWeekday(Weekday.MONDAY);

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Station WHERE Station.stationID = Stop.stationID AND Station.name = ?"
				+ " AND DAYOFWEEK(timetabledTime) = ?";

		assertThat(request.getSQL(), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndWeekdayAndHour() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationName("myStationName"));
		request.setWeekday(Weekday.MONDAY);
		request.setHour(1);

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Station WHERE Station.stationID = Stop.stationID AND Station.name = ?"
				+ " AND DAYOFWEEK(timetabledTime) = ? AND HOUR(timetabledTime) = ?";

		assertThat(request.getSQL(), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndHour() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationName("myStationName"));
		request.setHour(1);

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Station WHERE Station.stationID = Stop.stationID AND Station.name = ?"
				+ " AND HOUR(timetabledTime) = ?";

		assertThat(request.getSQL(), Is.is(sql));
	}

	@Test
	public void sqlStationNameAndHourAndLineID() throws Exception
	{
		final DelayRequestDB request = new DelayRequestDB(new StationName("myStationName"));
		request.setHour(1);
		request.setLineID(9);

		final String sql = "SELECT (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay "
				+ "FROM Stop, Station WHERE Station.stationID = Stop.stationID AND Station.name = ?"
				+ " AND HOUR(timetabledTime) = ? AND lineID = ?";

		assertThat(request.getSQL(), Is.is(sql));
	}
}
