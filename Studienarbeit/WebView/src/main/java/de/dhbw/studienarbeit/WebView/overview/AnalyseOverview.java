package de.dhbw.studienarbeit.WebView.overview;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.AnalyseChart;
import de.dhbw.studienarbeit.WebView.components.AnalyseChartDialog;
import de.dhbw.studienarbeit.WebView.requests.RequestGridData;

@Route("analyse")
public class AnalyseOverview extends Overview
{
	private static final long serialVersionUID = 1L;
	private int numDataset = 0;

	private AnalyseChart chart = new AnalyseChart();

	private Div divChart = new Div();

	private Grid<RequestGridData> requestGrid;
	
	private List<RequestGridData> requests = new ArrayList<>();

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
		requestGrid.setSelectionMode(SelectionMode.SINGLE);
		requestGrid.addColumn(e -> e.getNumber()).setHeader("Nummer").setSortable(false);
		requestGrid.addColumn(e -> e.toString()).setHeader("Analyse").setSortable(false);
		requestGrid.addColumn(e -> e.getCount()).setHeader("Anzahl").setSortable(false);
		requestGrid.addColumn(e -> e.getCanceledPercentage() + "%").setHeader("Ausfälle").setSortable(false);
		requestGrid.addColumn(e -> e.getOnTimePercentage() + "%").setHeader("Pünktlich").setSortable(false);
		requestGrid.addColumn(new NativeButtonRenderer<RequestGridData>("löschen", item -> delete(item)));

		requestGrid.setDetailsVisibleOnClick(true);
		requestGrid.setItemDetailsRenderer(new TextRenderer<>(e -> e.toString()));
		
		requestGrid.setHeight("20vh");

		VerticalLayout layout = new VerticalLayout(buttons, new Div(requestGrid), divChart);
		layout.setAlignItems(Alignment.STRETCH);
		layout.setSizeFull();

		setContent(layout);
	}

	private void delete(RequestGridData item)
	{
		requests.remove(item);
		chart.removeDataset(item);
		divChart.removeAll();
		divChart.add(chart.getChart());
		requestGrid.setItems(requests);
	}

	private void addData()
	{
		Dialog dialog = new AnalyseChartDialog(this);
		dialog.open();
	}

	private void emptyDiagram()
	{
		numDataset = 0;
		divChart.removeAll();
		requests.forEach(chart::removeDataset);
		requests.clear();
		requestGrid.setItems(requests);
	}

	public void addDataToGrid(RequestGridData data)
	{
		divChart.removeAll();
		chart.addDataset(data);
		divChart.add(chart.getChart());
		requests.add(data);
		requestGrid.setItems(requests);
		numDataset++;
	}
	
	public int getNumDataset()
	{
		return numDataset;
	}
}
