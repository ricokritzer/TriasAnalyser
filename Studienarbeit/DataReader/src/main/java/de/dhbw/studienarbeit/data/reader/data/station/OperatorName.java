package de.dhbw.studienarbeit.data.reader.data.station;

public class OperatorName
{
	private final String name;

	public OperatorName(String name)
	{
		super();
		this.name = name;
	}

	public final String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
