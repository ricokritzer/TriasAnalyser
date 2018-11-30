package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;

import de.dhbw.studienarbeit.data.reader.database.DelayWeatherDB;
import de.dhbw.studienarbeit.web.data.Data;

public class DelayWeatherDiv extends Div
{
	private static final long serialVersionUID = 1L;

	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * 60;
	private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * 24;

	private final Grid<DelayWeatherDB> grid = new Grid<>();
	private final TextField field = new TextField();

	public DelayWeatherDiv()
	{
		super();
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		field.setLabel("Stand");
		field.setReadOnly(true);
		field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getDelaysWeatherLastUpdate()));
		layout.add(field);

		grid.addColumn(db -> db.getWind()).setHeader("Wind")
				.setComparator((db1, db2) -> Double.compare(db1.getWind(), db2.getWind())).setSortable(true);
		grid.addColumn(db -> db.getHumidity()).setHeader("Luftfeuchtigkeit")
				.setComparator((db1, db2) -> Double.compare(db1.getHumidity(), db2.getHumidity())).setSortable(true);
		grid.addColumn(db -> db.getPressure()).setHeader("Druck")
		.setComparator((db1, db2) -> Double.compare(db1.getPressure(), db2.getPressure())).setSortable(true);
		grid.addColumn(db -> db.getClouds()).setHeader("Bewölkung")
		.setComparator((db1, db2) -> Double.compare(db1.getClouds(), db2.getClouds())).setSortable(true);
		grid.addColumn(db -> convertTimeToString(db.getAverage())).setHeader("Durchschnitt")
				.setComparator((db1, db2) -> Double.compare(db1.getAverage(), db2.getAverage())).setSortable(true);
		grid.addColumn(db -> convertTimeToString(db.getMaximum())).setHeader("Maximal")
				.setComparator((db1, db2) -> Double.compare(db1.getMaximum(), db2.getMaximum())).setSortable(true);

		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setDataProvider(DataProvider.ofCollection(Data.getDelaysWeather()));

		layout.add(grid);
		add(layout);

		setVisible(false);
	}

	private String convertTimeToString(double time)
	{
		if (time > SECONDS_PER_DAY)
		{
			final double days = time / SECONDS_PER_DAY;
			return Double.toString(round(days)) + " Tage";
		}
		if (time > SECONDS_PER_HOUR)
		{
			final double hours = time / SECONDS_PER_HOUR;
			return Double.toString(round(hours)) + " Stunden";
		}
		if (time > SECONDS_PER_MINUTE)
		{
			final double minutes = time / SECONDS_PER_MINUTE;
			return Double.toString(round(minutes)) + " Minuten";
		}
		return Double.toString(time) + " Sekunden";
	}

	private double round(double value)
	{
		return Math.round(value * 100) / 100.0;
	}
}
