package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituation;
import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituationData;
import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituationDataPart;
import de.dhbw.studienarbeit.data.trias.Situation;
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
		grid.addColumn(db -> db.getCountWithSituation().toString()).setHeader("Anzahl mit").setSortable(true);
		grid.addColumn(db -> db.getDelayWithSituation().toString()).setHeader("Durchschnittliche Verspätung mit").setSortable(true);
		grid.addColumn(db -> db.getCountWithoutSituation().toString()).setHeader("Anzahl ohne").setSortable(true);
		grid.addColumn(db -> db.getDelayWithoutSituation().toString()).setHeader("Durchschnittliche Verspätung ohne").setSortable(true);
		
		grid.setDataProvider(DataProvider.ofCollection(Data.getDelaysSituation()));

		grid.setHeight("70vh");
		grid.setSelectionMode(SelectionMode.NONE);

		layout.add(grid);

		add(layout);
	}
}
