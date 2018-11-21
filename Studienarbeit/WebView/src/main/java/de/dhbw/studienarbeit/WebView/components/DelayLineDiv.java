package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;
import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;

import de.dhbw.studienarbeit.WebView.data.Data;
import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;

@PageTitle("Verspätungen")
public class DelayLineDiv extends Div
{
	private static final long serialVersionUID = 1L;

	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * 60;
	private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * 24;

	private final Grid<DelayLineDB> grid = new Grid<>();

	public DelayLineDiv()
	{
		super();
		setSizeFull();

		final VerticalLayout layout = new VerticalLayout();

		layout.add(new TextArea(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getLastUpdate())));

		new Data(); // TODO remove as soon as possible
		final List<DelayLineDB> delayData = Data.getDelaysLine();

		grid.addColumn(db -> db.getLineName()).setHeader("Linie").setSortable(true);
		grid.addColumn(db -> db.getLineDestination()).setHeader("Ziel").setSortable(true);
		grid.addColumn(db -> convertTimeToString(db.getAverage())).setHeader("Durchschnitt").setSortable(true);
		grid.addColumn(db -> convertTimeToString(db.getMaximum())).setHeader("Maximum").setSortable(true);

		grid.setItems(delayData);

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
