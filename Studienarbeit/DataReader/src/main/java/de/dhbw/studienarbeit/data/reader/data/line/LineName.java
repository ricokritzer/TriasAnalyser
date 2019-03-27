package de.dhbw.studienarbeit.data.reader.data.line;

public class LineName
{
	private final String value;

	public LineName(String value)
	{
		super();
		this.value = value;
	}

	/*
	 * @Deprecated use toString instead.
	 */
	@Deprecated
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
