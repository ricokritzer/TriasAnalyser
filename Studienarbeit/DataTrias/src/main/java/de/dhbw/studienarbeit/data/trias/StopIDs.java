package de.dhbw.studienarbeit.data.trias;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StopIDs {
	public final Map<String, String> stops = new HashMap<>();

	public StopIDs() {
		stops.put("de:08121:1123:1:Moltk", "Heilbronn Harmonie");
		stops.put("de:08125:31502:2:ZGRöt", "Neckarsulm Süd");
		stops.put("de:08225:9180:1:S1Bus", "Neckarelz Bahnhof");
		stops.put("de:08237:1050:1:Mast1", "Freudenstadt Hauptbahnhof");
	}

	public Set<String> getAll()
	{
		return stops.keySet();
	}
}
