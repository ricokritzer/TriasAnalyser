package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;

import de.dhbw.studienarbeit.data.reader.data.line.DelayLineData;
import de.dhbw.studienarbeit.web.data.Data;

@PageTitle("Verspätungen")
public class DelayLineDiv extends Div
{
	private static final long serialVersionUID = 8L;

	private final Grid<DelayLineData> grid = new Grid<>();
	private final TextField field = new TextField();

	public DelayLineDiv()
	{
		super();
		setSizeFull();
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		field.setLabel("Stand");
		field.setReadOnly(true);
		field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getDelayLineWO().getLastUpdated()));
		layout.add(field);

		grid.addColumn(db -> db.getLineName()).setHeader("Linie").setSortable(true);
		grid.addColumn(db -> db.getLineDestination()).setHeader("Ziel").setSortable(true);
		grid.addColumn(db -> db.getAverage().toString()).setHeader("Durchschnitt")
				.setComparator((db1, db2) -> db1.getAverage().compareTo(db2.getAverage())).setSortable(true);
		grid.addColumn(db -> db.getMaximum().toString()).setHeader("Maximum")
				.setComparator((db1, db2) -> db1.getMaximum().compareTo(db2.getMaximum())).setSortable(true);

		grid.setDataProvider(DataProvider.ofCollection(Data.getDelayLineWO().getData()));

		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.NONE);

		layout.add(grid);
		
		add(layout);
	}
}
