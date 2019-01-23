package de.dhbw.studienarbeit.data.trias.analyse.lines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationNeighboursKVV
{
	private static Map<String, String> lineIDs;
	private static Map<String, List<String>> stationNeighbors;

	public static void main(String[] args)
	{
		getAllStationNeighbors();
	}

	public static void getAllStationNeighbors()
	{
		stationNeighbors = new HashMap<>();
		lineIDs  = new HashMap<>();
		getLineIDs();
		getRoutes();
	}

	private static void getRoutes()
	{
		try
		{
			File file = new File("stop_times.txt");
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			while (reader.ready())
			{
				String line = reader.readLine();
				
				// Nur Zeilen mit Daten auslesen
				if (line.startsWith("\""))
				{
					String[] splitted = line.split("\",\"");
					String tripID = splitted[0].replaceAll("\"", "");
					String stationIDLong = splitted[3].replaceAll("\"", "");
					String[] stationIDSplitted = stationIDLong.split(":");
					String stationID = stationIDSplitted[0] + ":" + stationIDSplitted[1] + ":" + stationIDSplitted[2];

					addStationNeighbour(tripID, stationID);
				}
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		for (String s : stationNeighbors.keySet())
		{
			System.out.println(s + " hat:");
			for (String str : stationNeighbors.get(s))
			{
				System.out.println(str);
			}
		}
	}

	private static void getLineIDs()
	{
		try
		{
			File file = new File("trips.txt");
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			while (reader.ready())
			{
				String line = reader.readLine();
				
				// Nur Zeilen mit Daten auslesen
				if (line.startsWith("\""))
				{
					String[] splitted = line.split("\",\"");
					String tripID = splitted[2].replaceAll("\"", "");
					String destination = splitted[3].replaceAll("\"", "");

					lineIDs.put(tripID, destination);
				}
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		for (String s : lineIDs.keySet())
		{
			System.out.println(s + " ist " + lineIDs.get(s));
		}
	}

	private static void addStationNeighbour(String tripID, String stationID)
	{
		if (!stationNeighbors.keySet().contains(tripID))
		{
			stationNeighbors.put(tripID, new ArrayList<>());
		}
		stationNeighbors.get(tripID).add(stationID);
	}
}
