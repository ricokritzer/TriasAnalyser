package de.dhbw.studienarbeit.WebView.tiles;

import java.util.Comparator;

import com.vaadin.flow.function.ValueProvider;

public class GridColumn<T>
{
	private final String name;
	private final ValueProvider<T, String> valueProvider;
	private final Comparator<T> comparator;

	public GridColumn(String name, ValueProvider<T, String> valueProvider, Comparator<T> comparator)
	{
		super();
		this.name = name;
		this.valueProvider = valueProvider;
		this.comparator = comparator;
	}

	public String getName()
	{
		return name;
	}

	public ValueProvider<T, String> getValueProvider()
	{
		return valueProvider;
	}

	public Comparator<T> getComparator()
	{
		return comparator;
	}
}
