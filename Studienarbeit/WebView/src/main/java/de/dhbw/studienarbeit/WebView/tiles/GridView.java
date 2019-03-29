package de.dhbw.studienarbeit.WebView.tiles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;

public class GridView<T> extends Div
{
	private static final long serialVersionUID = 1L;

	public GridView(List<T> elements, List<GridColumn<T>> columns, Date lastUpdated)
	{
		super();
		setSizeFull();
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		final TextField field = new TextField();
		field.setLabel("Stand");
		field.setReadOnly(true);
		field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(lastUpdated));
		layout.add(field);

		final Grid<T> grid = new Grid<>();
		columns.forEach(colum -> grid.addColumn(colum.getValueProvider()).setHeader(colum.getName()).setSortable(true)
				.setComparator(colum.getComparator()));
		grid.setDataProvider(DataProvider.ofCollection(elements));
		grid.setHeight("70vh");
		grid.setSelectionMode(SelectionMode.NONE);

		layout.add(grid);

		add(layout);
	}
}
