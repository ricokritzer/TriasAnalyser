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

		Grid<RequestGridData> requestGrid = new Grid<>();
		requestGrid.addColumn(e -> requests.indexOf(e)).setHeader("Nummer").setSortable(false);
		requestGrid.addColumn(e -> e.toString()).setHeader("Request").setSortable(false);
		requestGrid.addColumn(e -> e.getCount()).setHeader("Anzahl").setSortable(false);
		requestGrid.addColumn(e -> e.getCanceledPercentage() + "%").setHeader("Ausfälle").setSortable(false);
		requestGrid.addColumn(e -> e.getOnTimePercentage() + "%").setHeader("Pünktlich").setSortable(false);
		
		requestGrid.setSizeFull();
		
		divChart = new Div();
		
		VerticalLayout layout = new VerticalLayout(buttons, requestGrid, divChart);
		layout.setAlignItems(Alignment.STRETCH);
		layout.setSizeFull();
		
		setContent(layout);
	}

	private void addData()
	{
		divChart.removeAll();
		try
		{
			chart.addDataset(new RequestDB(new StationID("de:08212:1")).getDelays());
			chart.addDataset(new RequestDB(new StationID("de:08212:2")).getDelays());
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
