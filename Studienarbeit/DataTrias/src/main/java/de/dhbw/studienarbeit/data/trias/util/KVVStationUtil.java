package de.dhbw.studienarbeit.data.trias.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;

public class KVVStationUtil
{
	private static final String KVV = "kvv";

	public static void main(String[] args)
	{
		getAllStations();
	}

	public static List<StationSaveable> getAllStations()
	{
		List<StationSaveable> stations = new ArrayList<>();
		try
		{
			File file = new File("stops_kvv.txt");
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
					stations.add(new StationSaveable(stationID, name, lat, lon, KVV, true));
				}
			}
			reader.close();
			stations.forEach(s -> {
				DatabaseSaver.saveData(s);
				System.out.println(s.getName() + " gespeichert");
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return stations;
	}

	private static boolean testAlreadySaved(List<StationSaveable> stations, String stationID)
	{
		for (StationSaveable s : stations)
		{
			if (s.getStationID().equals(stationID))
			{
				return true;
			}
		}
		return false;
	}
}
