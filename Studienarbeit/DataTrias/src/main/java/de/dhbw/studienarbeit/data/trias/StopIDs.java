package de.dhbw.studienarbeit.data.trias;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StopIDs {
	public final Map<String, String> stops = new HashMap<>();

	public StopIDs() {
		stops.put("de:08212:37", "Karlsruhe Mühlburger Tor");
		stops.put("de:08212:321", "KA Mühlburger Tor (Grashofstr)");
		stops.put("de:08212:60", "Karlsruhe Europapl. (Karlstr.)");
		stops.put("de:08212:31", "Karlsruhe Europapl.(Kaiserstr)");
		stops.put("de:08212:99", "Karlsruhe Herrenstraße");
		stops.put("de:8212:1", "Karlsruhe Marktplatz");
		stops.put("de:08212:80", "Karlsruhe Kronenpl.(Erler-Str)");
		stops.put("de:08212:2", "Karlsruhe Kronenpl.(Kaiserstr)");
		stops.put("de:08212:3", "Karlsruhe Durlacher Tor/KIT");
		stops.put("de:08212:6", "Karlsruhe Gottesauer Platz/BGV");
		stops.put("de:08212:401", "Karlsruhe Karl-Wilhelm-Platz");
	}

	public Set<String> getAll()
	{
		return stops.keySet();
	}
}
