package de.dhbw.studienarbeit.data.reader.data.weather;

public class DelayCorrelationData<T> extends DelayWeatherCorrelationData
{
	private final Class<T> clazz;

	public DelayCorrelationData(double value, Class<T> clazz)
	{
		super(value);
		this.clazz = clazz;
	}

	public String getType()
	{
		return clazz.getName();
	}
}
