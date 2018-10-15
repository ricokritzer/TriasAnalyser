package de.dhbw.studienarbeit;

import java.util.HashMap;
import java.util.Map;

public class StopIDs {
	public final Map<String, String> stops = new HashMap<>();

	public StopIDs() {
		stops.put("de:8212:1", "Karlsruhe Marktplatz");
		stops.put("de:8212:12", "Karlsruhe Duale Hochschule");
		stops.put("de:8212:90", "Karlsruhe Hauptbahnhof");
	}
}
