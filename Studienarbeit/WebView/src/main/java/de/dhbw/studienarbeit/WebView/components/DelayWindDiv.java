package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;

import de.dhbw.studienarbeit.data.reader.data.weather.wind.DelayWindData;
import de.dhbw.studienarbeit.web.data.Data;

public class DelayWindDiv extends Div
{
	private static final long serialVersionUID = 15L;

	private final Grid<DelayWindData> grid = new Grid<>();
	private final TextField field = new TextField();

	public DelayWindDiv()
	{
		super();
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		field.setLabel("Stand");
		field.setReadOnly(true);
		field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getDelayCloudsWO().getLastUpdated()));
		layout.add(field);

		grid.addColumn(db -> db.getValue()).setHeader("Windstärke")
				.setComparator((db1, db2) -> Double.compare(db1.getValue(), db2.getValue())).setSortable(true);
		grid.addColumn(db -> db.getAverage().toString()).setHeader("Durchschnitt")
				.setComparator((db1, db2) -> db1.getAverage().compareTo(db2.getAverage())).setSortable(true);
		grid.addColumn(db -> db.getMaximum().toString()).setHeader("Maximal")
				.setComparator((db1, db2) -> db1.getMaximum().compareTo(db2.getMaximum())).setSortable(true);

		grid.setHeight("70vh");
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setDataProvider(DataProvider.ofCollection(Data.getDelayWindWO().getData()));

		layout.add(grid);
		add(layout);
	}
}
