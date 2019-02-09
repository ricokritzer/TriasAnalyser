package de.dhbw.studienarbeit.data.trias.analyse.lines;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SimpleStation
{
	private final Set<SimpleStation> neighbours = new HashSet<>();
	private final String id;

	public SimpleStation(String id)
	{
		super();
		this.id = id;
	}

	public Set<SimpleStation> getNeighbours()
	{
		return this.neighbours;
	}

	public void addNeighbourIfNotExists(SimpleStation station)
	{
		neighbours.add(station);
	}
	
	public String getID()
	{
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (super.equals(obj))
		{
			return true;
		}
		if (obj instanceof SimpleStation)
		{
			SimpleStation other = (SimpleStation) obj;
			return other.getID().equals(this.id);
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(this.id);
	}
}
