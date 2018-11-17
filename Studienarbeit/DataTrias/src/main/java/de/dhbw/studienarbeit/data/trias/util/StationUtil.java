package de.dhbw.studienarbeit.data.trias.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.saver.Saver;
import de.dhbw.studienarbeit.data.reader.database.StationDB;

public class StationUtil
{
	private static final String KVV = "kvv";

	public static void main(String[] args)
	{
		getAllStations();
	}

	public static List<StationDB> getAllStations()
	{
		List<StationDB> stations = new ArrayList<>();
		try
		{
			File file = new File("stops.txt");
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			while (reader.ready())
			{
				String stop = reader.readLine();
				if (stop.startsWith("\"de"))
				{
					String[] splittedStop = stop.split("\",\"");
					String stationIDLong = splittedStop[0].replaceAll("\"", "");
					String[] stationIDSplitted = stationIDLong.split(":");
					String stationID = stationIDSplitted[0] + ":" + stationIDSplitted[1] + ":" + stationIDSplitted[2];

					String name = splittedStop[1];
					Double lat = Double.valueOf(splittedStop[2]);
					Double lon = Double.valueOf(splittedStop[3].replaceAll("\"", ""));

					if (testAlreadySaved(stations, stationID))
					{
						continue;
					}
					if (name.isEmpty())
					{
						continue;
					}
					stations.add(new StationDB(stationID, name, lat, lon, KVV, false));
				}
			}
			reader.close();
			Saver saver = new DatabaseSaver();
			stations.forEach(station -> {
				try
				{
					saver.save(station);
				}
				catch (IOException e)
				{
					// ignore.
				}
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return stations;
	}

	private static boolean testAlreadySaved(List<StationDB> stations, String stationID)
	{
		for (StationDB s : stations)
		{
			if (s.getStationID().equals(stationID))
			{
				return true;
			}
		}
		return false;
	}
}
