package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;
import java.util.Optional;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.shared.communication.PushMode;

import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;
import de.dhbw.studienarbeit.web.data.Data;

@PageTitle("Verspätungen")
public class DelayStationDiv extends Div
{
	private static final long serialVersionUID = 1L;

	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * 60;
	private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * 24;

	private final Grid<DelayStationDB> grid = new Grid<>();
	private final TextField field = new TextField();

	public DelayStationDiv()
	{
		super();
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();

		field.setLabel("Stand");
		field.setReadOnly(true);
		layout.add(field);
		
		grid.addColumn(db -> db.getStationName()).setHeader("Station").setSortable(false);
		grid.addColumn(db -> db.getOperator()).setHeader("Verkehrsverbund").setSortable(false);
		grid.addColumn(db -> convertTimeToString(db.getAverage())).setHeader("Durchschnitt")
				.setComparator((db1, db2) -> Double.compare(db1.getAverage(), db2.getAverage())).setSortable(true);
		grid.addColumn(db -> convertTimeToString(db.getMaximum())).setHeader("Maximal")
				.setComparator((db1, db2) -> Double.compare(db1.getMaximum(), db2.getMaximum())).setSortable(true);
		grid.addColumn(db -> convertToRating(db.getCount())).setHeader("Datengrundlage")
				.setComparator((db1, db2) -> Integer.compare(db1.getCount(), db2.getCount())).setSortable(true);

		grid.setHeightByRows(true);
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.NONE);

		layout.add(grid);
		add(layout);
		
		update();
		
		setVisible(false);
	}
	
	private String convertToRating(int count)
	{
		if (count < 10)
		{
			return "gering";
		}
		if (count > 100)
		{
			return "hoch";
		}
		return "mittel";
	}

	private void update()
	{
		UI ui = getUI().orElse(UI.getCurrent());
		Optional.ofNullable(ui).ifPresent(currentUI -> currentUI.access(() -> {
			currentUI.getPushConfiguration().setPushMode(PushMode.MANUAL);
			grid.setItems(Data.getDelaysStation());
			field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getDelaysStationLastUpdate()));
			currentUI.push();
		}));
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
