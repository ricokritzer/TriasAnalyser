package de.dhbw.studienarbeit.data.reader.data.vehicletype;

public class VehicleType
{
	private final String value;

	public VehicleType(String value)
	{
		super();
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return value;
	}
}
