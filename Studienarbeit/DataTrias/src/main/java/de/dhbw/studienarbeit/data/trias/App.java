package de.dhbw.studienarbeit.data.trias;

import java.util.List;

import de.dhbw.studienarbeit.data.trias.util.StationUtil;

public class App 
{
    public static void main( String[] args )
    {
    	List<Station> stations = StationUtil.getAllStations();
    	String[] stationIDs = new String[stations.size()];
    	for (int i = 0; i < stations.size(); i++)
    	{
    		stationIDs[i] = stations.get(i).getStationID();
    	}
		DatabaseConnector.createFile();
		new DataCollectorTrias(stationIDs).run();
    }
}
