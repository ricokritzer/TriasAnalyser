package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.Manageable;

public class Station implements Manageable
{
	private String stationID;
	private String name;
	private Double lat;
	private Double lon;
	private String operator;
	private List<Stop> previousStops = new ArrayList<>();
	private List<Stop> currentStops = new ArrayList<>();
	private Date nextUpdate;
	
	private static final Logger LOGGER = Logger.getLogger(Station.class.getName());

	public Station(String stationID, String name, Double lat, Double lon, String operator)
	{
		this.stationID = stationID;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.operator = operator;
	}

	public String getStationID()
	{
		return stationID;
	}

	public String getName()
	{
		return name;
	}

	public Double getLat()
	{
		return lat;
	}

	public Double getLon()
	{
		return lon;
	}

	public String getOperator()
	{
		return operator;
	}

	public void getSQLQuerry()
	{
		List<Stop> stopsToSave = new ArrayList<>();
		for (Stop stop : previousStops)
		{
			if (!currentStops.contains(stop))
			{
				stopsToSave.add(stop);
			}
		}
		LOGGER.log(Level.FINE, "Stops gespeichert:");
		for (Stop stop : stopsToSave)
		{
			LOGGER.log(Level.FINE, stop.toString());
			new DatabaseSaver().save(stop);
		}
	}

	@Override
	public Date nextUpdate()
	{
		LOGGER.log(Level.FINE, nextUpdate.toString());
		return nextUpdate;
	}

	private void updateData(ApiKey apiKey) throws IOException
	{
		previousStops = currentStops;
		TriasXMLRequest request = new TriasXMLRequest(apiKey, this);
		try
		{
			currentStops = request.getResponse();
		}
		catch (IOException e)
		{
			currentStops = previousStops;
		}
		LOGGER.log(Level.FINE, "Stops gelesen: ");
		currentStops.forEach(s -> LOGGER.log(Level.FINE, s.toString()));
		calculateNextUpdate();
	}

	/**
	 * sets the value of nextUpdate. Stations are update one minute before the next
	 * train with real time data arrives. If no train with real time data arrives in
	 * the next 5 minutes, the station is updated 5 minutes before the next train
	 * arrives. If no trains with real time data arrive in the next 2 hours, the
	 * station is updated in 2 hours.
	 */
	private void calculateNextUpdate()
	{
		if (nextUpdate == null)
		{
			nextUpdate = new Date();
			return;
		}

		Calendar cal = Calendar.getInstance();
		if (currentStops.isEmpty())
		{
			cal.setTime(nextUpdate);
			cal.add(Calendar.HOUR_OF_DAY, 2);
			cal.add(Calendar.HOUR_OF_DAY, 1);
			nextUpdate = cal.getTime();
			return;
		}

		Stop nextStop = currentStops.get(0);
		cal.setTime(nextStop.getRealTime());
		cal.add(Calendar.MINUTE, -5);
		cal.add(Calendar.HOUR_OF_DAY, 1);

		Calendar inFiveMinutes = Calendar.getInstance();
		inFiveMinutes.setTime(new Date());
		inFiveMinutes.add(Calendar.MINUTE, 5);

		if (cal.before(inFiveMinutes))
		{
			cal.add(Calendar.MINUTE, 4);
			while (cal.getTime().before(new Date()))
			{
				cal.add(Calendar.MINUTE, 1);
			}
			nextUpdate = cal.getTime();
			return;
		}

		nextUpdate = cal.getTime();
	}

	@Override
	public void updateAndSaveData(ApiKey apiKey) throws IOException
	{
		updateData(apiKey);
		getSQLQuerry();
	}
}
