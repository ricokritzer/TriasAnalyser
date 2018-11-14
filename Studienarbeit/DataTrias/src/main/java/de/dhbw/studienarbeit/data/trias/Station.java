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
	private boolean updated;

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

	public void saveStops() throws IOException
	{
		for (Stop stop : previousStops)
		{
			if (!currentStops.contains(stop) && stop.getRealTime().isPresent() && !stop.getRealTime().get().equals(new Date(0)))
			{
				if (!stop.getRealTime().isPresent())
				{
					new DatabaseSaver().save(stop);
					continue;
				}
				if (!stop.getRealTime().get().equals(new Date(0)))
				{
					new DatabaseSaver().save(stop);
				}
			}
		}
	}

	@Override
	public Date nextUpdate()
	{
		return calculateNextUpdate();
	}

	private void updateStops(ApiKey apiKey) throws IOException
	{
		previousStops = currentStops;
		TriasXMLRequest request = new TriasXMLRequest(apiKey, this);
		try
		{
			currentStops = request.getResponse();
			updated = true;
		}
		catch (IOException e)
		{
			currentStops = previousStops;
			updated = false;
			throw new IOException("Station " + name + " could not be updated", e);
		}
	}

	/**
	 * sets the value of nextUpdate. Stations are update one minute before the next
	 * train with real time data arrives. If no train with real time data arrives in
	 * the next 5 minutes, the station is updated 5 minutes before the next train
	 * arrives. If no trains with real time data arrive in the next 2 hours, the
	 * station is updated in 2 hours.
	 */
	private Date calculateNextUpdate()
	{
		if (nextUpdate == null)
		{
			return initialUpdate();
		}

		if (currentStops.isEmpty())
		{
			if (!updated)
			{
				return initialUpdate();
			}
			return tomorrow();
		}

		Stop nextStop = currentStops.get(0);
		
		// all Trains are cancelled
		if (!nextStop.getRealTime().isPresent())
		{
			return timeOfLastPlannedTrain();
		}

		// no Train with realtime data
		if (nextStop.getRealTime().get().equals(new Date(0)))
		{
			return timeOfNextPlannedTrain(nextStop);
		}

		return fiveMinutesBeforeNextTrain(nextStop);
	}

	private Date fiveMinutesBeforeNextTrain(Stop nextStop)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(nextStop.getRealTime().get());
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
			LOGGER.log(Level.FINE, "next Update for " + name + " " + nextUpdate);
			return nextUpdate;
		}

		nextUpdate = cal.getTime();
		LOGGER.log(Level.FINE, "next Update for " + name + " " + nextUpdate);
		return nextUpdate;
	}

	private Date timeOfNextPlannedTrain(Stop nextStop)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(nextStop.getTimeTabledTime());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.add(Calendar.MINUTE, -1);
		while (cal.getTime().before(new Date()))
		{
			cal.add(Calendar.MINUTE, 1);
		}
		nextUpdate = cal.getTime();
		LOGGER.log(Level.FINE, "next Update for " + name + " " + nextUpdate);
		return nextUpdate;
	}

	private Date timeOfLastPlannedTrain()
	{
		Calendar cal = Calendar.getInstance();
		Stop lastStop = currentStops.get(currentStops.size() - 1);
		cal.setTime(lastStop.getTimeTabledTime());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		nextUpdate = cal.getTime();
		LOGGER.log(Level.FINE, "next Update for " + name + " " + nextUpdate);
		return nextUpdate;
	}

	private Date tomorrow()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(nextUpdate);
		cal.add(Calendar.HOUR_OF_DAY, 24);
		nextUpdate = cal.getTime();
		LOGGER.log(Level.FINE, "next Update for " + name + " " + nextUpdate);
		return nextUpdate;
	}

	private Date initialUpdate()
	{
		nextUpdate = new Date();
		LOGGER.log(Level.FINE, "next Update for " + name + " " + nextUpdate);
		return nextUpdate;
	}

	@Override
	public void updateAndSaveData(ApiKey apiKey) throws IOException
	{
		updateStops(apiKey);
		saveStops();
	}
}
