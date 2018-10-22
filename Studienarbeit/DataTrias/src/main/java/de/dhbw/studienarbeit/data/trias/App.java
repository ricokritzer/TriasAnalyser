package de.dhbw.studienarbeit.data.trias;

import java.util.Arrays;
import java.util.Set;

public class App 
{
    public static void main( String[] args )
    {
    	Set<String> set = new StopIDs().getAll();
		String[] stopIDs = set.toArray(new String[set.size()]);
		String[] stopIDs1 = Arrays.copyOfRange(stopIDs, 0, set.size()/2);
		String[] stopIDs2 = Arrays.copyOfRange(stopIDs, set.size()/2, stopIDs.length);
		DatabaseConnector.createFile();
		new DataCollectorTrias(stopIDs1).run();
		new DataCollectorTrias(stopIDs2).run();
    }
}
