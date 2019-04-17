package de.dhbw.studienarbeit.data.reader.data.situation;

public class SituationName
{
	private final String value;

	public SituationName(String value)
	{
		super();
		this.value = value;
	}

	public final String getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return value;
	}
}
