package de.dhbw.studienarbeit.WebView.overview;

import java.io.IOException;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.AnalyseChart;
import de.dhbw.studienarbeit.WebView.requests.RequestGridData;
import de.dhbw.studienarbeit.data.reader.data.request.RequestDB;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;

@Route("analyse")
public class AnalyseOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	List<RequestGridData> requests;
	AnalyseChart chart = new AnalyseChart();

	private Div divChart;

	private Grid<RequestGridData> requestGrid;

	public AnalyseOverview()
	{
		super();

		Button btnEmpty = new Button("Diagramm leeren", VaadinIcon.TRASH.create());
		btnEmpty.addClickListener(e -> emptyDiagram());
		btnEmpty.setSizeFull();
		Button btnAddData = new Button("Daten hinzufügen", VaadinIcon.PLUS_CIRCLE_O.create());
		btnAddData.addClickListener(e -> addData());
		btnAddData.setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout(btnEmpty, btnAddData);
		buttons.setAlignItems(Alignment.STRETCH);

		requestGrid = new Grid<>();
		requestGrid.addColumn(e -> e.getNumber()).setHeader("Nummer").setSortable(false);
		requestGrid.addColumn(e -> e.toString()).setHeader("Request").setSortable(false);
		requestGrid.addColumn(e -> e.getCount()).setHeader("Anzahl").setSortable(false);
		requestGrid.addColumn(e -> e.getCanceledPercentage() + "%").setHeader("Ausfälle").setSortable(false);
		requestGrid.addColumn(e -> e.getOnTimePercentage() + "%").setHeader("Pünktlich").setSortable(false);
		
		requestGrid.setHeight("20vh");
		
		divChart = new Div();
		
		VerticalLayout layout = new VerticalLayout(buttons, new Div(requestGrid), divChart);
		layout.setAlignItems(Alignment.STRETCH);
		layout.setSizeFull();
		
		setContent(layout);
	}

	private void addData()
	{
		divChart.removeAll();
		try
		{
			RequestDB requestDB = new RequestDB(new StationID("de:08212:1"));
			chart.addDataset(requestDB.getDelays());
			
			RequestGridData data = new RequestGridData(requestDB, 1);
			requestGrid.setItems(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		divChart.add(chart.getChart());
	}
	
	private void emptyDiagram()
	{
		divChart.removeAll();
	}
}
