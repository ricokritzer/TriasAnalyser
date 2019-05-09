package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;

import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituationData;
import de.dhbw.studienarbeit.web.data.Data;

public class DelaySituationDiv extends Div
{
	private static final long serialVersionUID = 1L;

	private final Grid<DelaySituationData> grid = new Grid<>();
	private final TextField field = new TextField();

	public DelaySituationDiv()
	{
		super();
		setSizeFull();
		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		field.setLabel("Stand");
		field.setReadOnly(true);
		field.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Data.getDelaysSituationLastUpdated()));
		layout.add(field);

		grid.addColumn(db -> db.getSituationName()).setHeader("Verkehrssituation").setSortable(true);
		grid.addColumn(db -> db.getCountWithSituation().toString() + "/" + db.getCountWithoutSituation().toString())
				.setHeader("Anzahl (mit/ohne Situation)").setSortable(true).setComparator((o1, o2) -> Long
						.compare(o1.getCountWithSituation().getValue(), o2.getCountWithSituation().getValue()));
		grid.addColumn(db -> db.getDelayWithSituation().toString() + "/" + db.getDelayWithoutSituation().toString())
				.setHeader("Durchschnittliche Verspätung (mit/ohne Situation)").setSortable(false);
		grid.addColumn(db -> db.getDifference().toString()).setHeader("Verspätungsänderung").setSortable(true)
				.setComparator(
						(o1, o2) -> Double.compare(o1.getDifference().getValue(), o2.getDifference().getValue()));
		
		grid.setDetailsVisibleOnClick(true);
		grid.setItemDetailsRenderer(new TextRenderer<DelaySituationData>(e -> e.getSituationName().toString()));

		grid.setDataProvider(DataProvider.ofCollection(Data.getDelaysSituation()));

		grid.setHeight("70vh");
		grid.setSelectionMode(SelectionMode.NONE);

		layout.add(grid);

		add(layout);
	}
}
