package de.dhbw.studienarbeit.data.helper.database.table;

import java.util.Objects;

public class LineCache
{
	private String name;
	private String destination;

	public LineCache(String name, String destination)
	{
		super();
		this.name = name;
		this.destination = destination;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof LineCache)
		{
			final LineCache other = (LineCache) obj;
			return other.destination.equals(destination) && other.name.equals(name);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, destination);
	}
}
