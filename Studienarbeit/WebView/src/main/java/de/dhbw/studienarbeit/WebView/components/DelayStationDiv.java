package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;

import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.web.data.Data;

@PageTitle("Verspätungen")
public class DelayStationDiv extends Div
{
	private static final long serialVersionUID = 7L;

	private final Grid<DelayStationData> grid = new Grid<>();
	private final TextField field = new TextField();

	public DelayStationDiv()
	{
		super();
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		field.setLabel("Stand");
		field.setReadOnly(true);
		field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getDelaysStationWO().getLastUpdated()));
		layout.add(field);

		grid.addColumn(db -> db.getName().getStationName()).setHeader("Station").setSortable(true);
		grid.addColumn(db -> db.getOperator()).setHeader("Verkehrsverbund").setSortable(true);
		grid.addColumn(db -> db.getAverage().toString()).setHeader("Durchschnitt")
				.setComparator((db1, db2) -> db1.getAverage().compareTo(db2.getAverage())).setSortable(true);
		grid.addColumn(db -> db.getMaximum().toString()).setHeader("Maximal")
				.setComparator((db1, db2) -> db1.getMaximum().compareTo(db2.getMaximum())).setSortable(true);
		grid.addColumn(db -> convertToRating(db.getCount())).setHeader("Datengrundlage")
				.setComparator((db1, db2) -> Integer.compare(db1.getCount(), db2.getCount())).setSortable(true);

		grid.setHeight("70vh");
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setDataProvider(DataProvider.ofCollection(Data.getDelaysStationWO().getData()));

		layout.add(grid);
		add(layout);
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
}
