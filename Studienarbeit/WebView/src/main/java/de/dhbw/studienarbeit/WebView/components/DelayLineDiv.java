package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;

import de.dhbw.studienarbeit.data.reader.database.DelayLineDB;
import de.dhbw.studienarbeit.web.data.Data;

@PageTitle("Verspätungen")
public class DelayLineDiv extends Div
{
	private static final long serialVersionUID = 1L;

	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * 60;
	private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * 24;

	private final Grid<DelayLineDB> grid = new Grid<>();
	private final TextField field = new TextField();

	public DelayLineDiv()
	{
		super();
		setSizeFull();
		final VerticalLayout layout = new VerticalLayout();

		field.setLabel("Stand");
		field.setReadOnly(true);
		field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getDelaysLineLastUpdate()));
		layout.add(field);

		grid.addColumn(db -> db.getLineName()).setHeader("Linie").setSortable(false);
		grid.addColumn(db -> db.getLineDestination()).setHeader("Ziel").setSortable(false);
		grid.addColumn(db -> convertTimeToString(db.getAverage())).setHeader("Durchschnitt")
				.setComparator(getAvgComparator()).setSortable(true);
		grid.addColumn(db -> convertTimeToString(db.getMaximum())).setHeader("Maximum")
				.setComparator(getMaxComparator()).setSortable(true);

		grid.setItems(Data.getDelaysLine());

		grid.setHeightByRows(true);
		grid.setSizeFull();

		layout.add(grid);
		add(layout);
		setVisible(false);
	}

	private Comparator<DelayLineDB> getMaxComparator()
	{
		return (o1, o2) -> {
			if (o1.getMaximum() > o2.getMaximum())
			{
				return 1;
			}
			if (o1.getMaximum() < o2.getMaximum())
			{
				return -1;
			}
			return 0;
		};
	}

	private Comparator<DelayLineDB> getAvgComparator()
	{
		return (o1, o2) -> {
			if (o1.getAverage() > o2.getAverage())
			{
				return 1;
			}
			if (o1.getAverage() < o2.getAverage())
			{
				return -1;
			}
			return 0;
		};
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
