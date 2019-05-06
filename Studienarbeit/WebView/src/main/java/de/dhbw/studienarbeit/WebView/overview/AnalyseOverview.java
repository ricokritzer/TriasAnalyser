package de.dhbw.studienarbeit.WebView.overview;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.pekkam.Canvas;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.AnalyseChart;
import de.dhbw.studienarbeit.WebView.components.AnalyseChartDialog;
import de.dhbw.studienarbeit.WebView.requests.RequestGridData;

@Route("analyse")
@JavaScript("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.js")
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

		requestGrid.setSizeFull();

		divChart.add(getNewCanvas());
		divChart.getStyle().set("position", "relative");

		VerticalLayout upperHalf = new VerticalLayout(buttons, requestGrid);
		upperHalf.setAlignItems(Alignment.STRETCH);
		upperHalf.setSizeFull();

		SplitLayout layout = new SplitLayout(upperHalf, divChart);
		layout.setOrientation(Orientation.VERTICAL);
		layout.setSizeFull();
		layout.setSplitterPosition(30);
		
		setContent(layout);
		getUI().orElse(UI.getCurrent()).getPage().executeJavaScript(
				"var ctx = document.getElementById('chart-canvas').getContext('2d'); var myChart = new Chart(ctx, "
						+ new AnalyseChart().getChart() + ");");
	}

	private void delete(RequestGridData item)
	{
		requests.remove(item);
		chart.removeDataset(item);
		drawNewChart(chart);
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
		drawNewChart(new AnalyseChart());
		requests.forEach(chart::removeDataset);
		requests.clear();
		requestGrid.setItems(requests);
	}

	public void addDataToGrid(RequestGridData data)
	{
		chart.addDataset(data);
		drawNewChart(chart);
		requests.add(data);
		requestGrid.setItems(requests);
		numDataset++;
	}

	private void drawNewChart(AnalyseChart analyseChart)
	{
		divChart.removeAll();
		divChart.add(getNewCanvas());
		getUI().orElse(UI.getCurrent()).getPage().executeJavaScript(
				"var ctx = document.getElementById('chart-canvas').getContext('2d'); var myChart = new Chart(ctx, "
						+ analyseChart.getChart() + ");");
	}

	private Component getNewCanvas()
	{
		Canvas canvas = new Canvas(100, 100);
		canvas.setId("chart-canvas");
		canvas.setSizeFull();
		return canvas;
	}

	public int getNumDataset()
	{
		return numDataset;
	}
}
