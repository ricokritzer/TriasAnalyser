package de.dhbw.studienarbeit.data.trias;

import java.util.Set;

public class App 
{
    public static void main( String[] args )
    {
    	Set<String> set = new StopIDs().getAll();
		String[] stopIDs = set.toArray(new String[set.size()]);
		DatabaseConnector.createFile();
		new DataCollectorTrias(stopIDs).run();
    }
}
