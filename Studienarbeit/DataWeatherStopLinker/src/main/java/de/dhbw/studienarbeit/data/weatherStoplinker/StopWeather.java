package de.dhbw.studienarbeit.data.weatherStoplinker;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class StopWeather implements Saveable
{
	private static final Logger LOGGER = Logger.getLogger(StopWeather.class.getName());

	private final long idx;

	public StopWeather(long idx)
	{
		this.idx = idx;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO StopWeather (stopID, weatherId) SELECT stopID, Weather.id FROM Stop, Station, Weather "
				+ "WHERE Stop.stationID = Station.stationID AND Stop.stopID = ? AND Stop.stopID NOT IN (SELECT stopID FROM StopWeather) "
				+ "AND Weather.lat = ROUND(Station.lat, 2) AND Weather.lon = ROUND(Station.lon, 2) AND Weather.timeStamp < Stop.realTime "
				+ "GROUP BY Stop.stopID, Stop.realTime, Weather.timeStamp, Weather.id "
				+ "HAVING count(Weather.timeStamp) > 0 AND max(Weather.timeStamp) > DATE_SUB(Stop.realTime,INTERVAL 30 MINUTE);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setLong(1, idx);
	}

	public void save()
	{
		DatabaseSaver.saveData(this);
		LOGGER.log(Level.FINE, "Stop with index " + idx + " linked to weather.");
	}
}