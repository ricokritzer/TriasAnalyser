package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StopDB
{
	private static final Logger LOGGER = Logger.getLogger(StopDB.class.getName());

	private final int stopID;
	private final String stationID;
	private final int lineID;
	private final Date timeTabledTime;
	private final Date realTime;

	public StopDB(int stopID, String stationID, int lineID, Date timeTabledTime, Date realTime)
	{
		super();
		this.stopID = stopID;
		this.stationID = stationID;
		this.lineID = lineID;
		this.timeTabledTime = timeTabledTime;
		this.realTime = realTime;
	}

	public long delay()
	{
		return timeTabledTime.getTime() - realTime.getTime();
	}

	public int getStopID()
	{
		return stopID;
	}

	public String getStationID()
	{
		return stationID;
	}

	public int getLineID()
	{
		return lineID;
	}

	public Date getTimeTabledTime()
	{
		return timeTabledTime;
	}

	public Date getRealTime()
	{
		return realTime;
	}

	public static final Optional<StopDB> getStop(ResultSet result)
	{
		try
		{
			final int stopID = result.getInt("stopID");
			final String stationID = result.getString("stationID");
			final int lineID = result.getInt("lineID");
			final Date timeTabledTime = result.getDate("timeTabledTime");
			final Date realTime = result.getDate("realTime");

			return Optional.of(new StopDB(stopID, stationID, lineID, timeTabledTime, realTime));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}
}
