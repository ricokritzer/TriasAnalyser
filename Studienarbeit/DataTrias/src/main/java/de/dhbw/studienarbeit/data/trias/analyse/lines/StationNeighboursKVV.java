package de.dhbw.studienarbeit.data.trias.analyse.lines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;
import de.dhbw.studienarbeit.data.trias.StationNeighbour;

public class StationNeighboursKVV
{
	private static Map<String, List<String>> stationNeighbours = new HashMap<>();
	private static Set<SimpleStation> stations = new HashSet<>();
	private static List<LineStation> lineStations = new ArrayList<>();

	public static void main(String[] args)
	{
		try
		{
			getAllStationNeighbours();
			saveNeighboursWithoutLines();
			saveStationNeighboursForLines();
		}
		catch (SQLException | IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void saveNeighboursWithoutLines()
	{
		for (SimpleStation station : stations)
		{
			for (SimpleStation neighbour : station.getNeighbours())
			{
				// DatabaseSaver.saveData(new StationNeighbour(station.getID(),
				// neighbour.getID(), 0));
				System.out.println("added neighbours " + station.getID() + " and " + neighbour.getID() + " - no line");
			}
		}
	}

	private static void saveStationNeighboursForLines() throws SQLException, IOException
	{
		DatabaseReader reader = new DatabaseReader();
		reader.select(new Consumer<ResultSet>()
		{

			@Override
			public void accept(ResultSet rs)
			{
				try
				{
					int lineID = rs.getInt(1);
					saveStationNeighboursForLine(lineID);
				}
				catch (SQLException | IOException e)
				{
					e.printStackTrace();
				}
			}
		}, reader.getPreparedStatement("SELECT lineID FROM Line WHERE lineID = 128"));

	}

	protected static void saveStationNeighboursForLine(int lineID) throws SQLException, IOException
	{
		lineStations = new ArrayList<>();
		List<LineStation> firstStationList = new ArrayList<>();
		List<LineStation> nextStationsList = new ArrayList<>();

		DatabaseReader reader = new DatabaseReader();

		reader.select(new Consumer<ResultSet>()
		{
			@Override
			public void accept(ResultSet rs)
			{
				try
				{
					Timestamp timestamp = rs.getTimestamp(2);
					timestamp = subtractOneHour(timestamp);
					firstStationList.add(new LineStation(rs.getString(1), lineID, timestamp));
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

			}
		}, reader.getPreparedStatement("SELECT stationID, timeTabledTime FROM Stop WHERE lineID = " + lineID
				+ " ORDER BY timeTabledTime LIMIT 1;"));

		LineStation firstStation = firstStationList.get(0);

		for (SimpleStation station : getStation(firstStation.getStationID()).getNeighbours())
		{
			reader.select(new Consumer<ResultSet>()
			{
				@Override
				public void accept(ResultSet rs)
				{
					try
					{
						Timestamp timestamp = rs.getTimestamp(2);
						timestamp = subtractOneHour(timestamp);
						nextStationsList.add(new LineStation(rs.getString(1), lineID, timestamp));
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}

				}
			}, reader.getPreparedStatement("SELECT stationID, timeTabledTime FROM Stop WHERE lineID = " + lineID
					+ " AND stationID = '" + station.getID() + "' ORDER BY timeTabledTime LIMIT 1;"));
		}

		LineStation neighbour1 = null;
		LineStation neighbour2 = null;

		if (nextStationsList.isEmpty())
		{
			// Hält nicht an Nachbarn --> Keine Bahn vom KVV
			return;
		}
		else if (nextStationsList.size() < 2)
		{
			// TODO entweder erster oder letzter Halt
		}
		else
		{
			neighbour1 = nextStationsList.get(0);
			neighbour2 = nextStationsList.get(1);
		}
		if (neighbour1.getFirstOccurence().before(neighbour2.getFirstOccurence()))
		{
			firstStation.setNextStation(neighbour1);
			neighbour2.setNextStation(firstStation);
			getPreviousNeighbour(neighbour2, firstStation, lineID);
			getNextNeighbour(neighbour1, firstStation, lineID);
		}
		else
		{
			neighbour1.setNextStation(firstStation);
			firstStation.setNextStation(neighbour2);
			getPreviousNeighbour(neighbour1, firstStation, lineID);
			getNextNeighbour(neighbour2, firstStation, lineID);
		}

		lineStations.add(firstStation);
		System.out.println("added " + firstStation.getStationID());
		saveLine(lineID);
	}

	private static void getNextNeighbour(LineStation station, LineStation previousNeighbour, int lineID)
			throws SQLException, IOException
	{
		List<LineStation> nextStationsList = new ArrayList<>();

		DatabaseReader reader = new DatabaseReader();
		for (SimpleStation ss : getStation(station.getStationID()).getNeighbours())
		{
			if (!(ss.getID().equals(previousNeighbour.getStationID()) || alreadySaved(ss.getID())))
			{
				reader.select(new Consumer<ResultSet>()
				{
					@Override
					public void accept(ResultSet rs)
					{
						try
						{
							Timestamp timestamp = rs.getTimestamp(2);
							timestamp = subtractOneHour(timestamp);
							nextStationsList.add(new LineStation(rs.getString(1), lineID, timestamp));
						}
						catch (SQLException e)
						{
							e.printStackTrace();
						}

					}
				}, reader.getPreparedStatement("SELECT stationID, timeTabledTime FROM Stop WHERE lineID = " + lineID
						+ " AND stationID = '" + ss.getID() + "' ORDER BY timeTabledTime LIMIT 1;"));
			}
		}

		if (nextStationsList.isEmpty())
		{
			// alle vorherigen Stops gefunden
			return;
		}
		
		for (LineStation next : nextStationsList)
		{
			station.setNextStation(next);
			lineStations.add(station);
			System.out.println("added " + station.getStationID());
			getNextNeighbour(next, station, lineID);
		}
	}

	private static void getPreviousNeighbour(LineStation station, LineStation nextNeighbour, int lineID)
			throws SQLException, IOException
	{
		List<LineStation> previousStationsList = new ArrayList<>();

		DatabaseReader reader = new DatabaseReader();
		for (SimpleStation ss : getStation(station.getStationID()).getNeighbours())
		{
			if (!(ss.getID().equals(nextNeighbour.getStationID()) || alreadySaved(ss.getID())))
			{
				reader.select(new Consumer<ResultSet>()
				{
					@Override
					public void accept(ResultSet rs)
					{
						try
						{
							Timestamp timestamp = rs.getTimestamp(2);
							timestamp = subtractOneHour(timestamp);
							previousStationsList.add(new LineStation(rs.getString(1), lineID, timestamp));
						}
						catch (SQLException e)
						{
							e.printStackTrace();
						}

					}
				}, reader.getPreparedStatement("SELECT stationID, timeTabledTime FROM Stop WHERE lineID = " + lineID
						+ " AND stationID = '" + ss.getID() + "' ORDER BY timeTabledTime LIMIT 1;"));
			}
		}

		if (previousStationsList.isEmpty())
		{
			// alle vorherigen Stops gefunden
			return;
		}

		for (LineStation previous : previousStationsList)
		{
			previous.setNextStation(station);
			lineStations.add(station);
			System.out.println("added " + station.getStationID());
			getPreviousNeighbour(previous, station, lineID);
		}
	}



	private static boolean alreadySaved(String id)
	{
		for (LineStation station : lineStations)
		{
			if (station.getStationID().equals(id))
			{
				return true;
			}
		}
		return false;
	}

	private static SimpleStation getStation(String stationID)
	{
		return getStation(new SimpleStation(stationID));
	}

	protected static Timestamp subtractOneHour(Timestamp timestamp)
	{
		long current = timestamp.getTime();
		long subtracted = current - (60 * 60 * 1000);
		return new Timestamp(subtracted);
	}

	private static void saveLine(int lineId)
	{
		for (LineStation station : lineStations)
		{
			if (station.getNextStation() != null)
			{
				DatabaseSaver.saveData(new StationNeighbour(station.getStationID(), station.getNextStation().getStationID(), lineId));
				System.out.println(station.getStationID() + " and " + station.getNextStation().getStationID());
			}
		}
	}

	public static void getAllStationNeighbours() throws SQLException, IOException
	{
		stationNeighbours = new HashMap<>();
		getRoutes();
		for (String s : stationNeighbours.keySet())
		{
			for (int i = 0; i < stationNeighbours.get(s).size() - 1; i++)
			{
				SimpleStation station = new SimpleStation(stationNeighbours.get(s).get(i));
				SimpleStation neighbour = new SimpleStation(stationNeighbours.get(s).get(i + 1));
				getStation(station).addNeighbourIfNotExists(neighbour);
			}
		}
	}

	private static SimpleStation getStation(SimpleStation station)
	{
		for (SimpleStation ss : stations)
		{
			if (ss.equals(station))
			{
				return ss;
			}
		}
		return null;
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

					stations.add(new SimpleStation(stationID));
					addStationToTrip(tripID, stationID);
				}
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void addStationToTrip(String tripID, String stationID)
	{
		if (!stationNeighbours.keySet().contains(tripID))
		{
			stationNeighbours.put(tripID, new ArrayList<>());
		}
		stationNeighbours.get(tripID).add(stationID);
	}
}
