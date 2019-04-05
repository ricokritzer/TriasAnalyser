package de.dhbw.studienarbeit.WebView.overview;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.AnalyseChart;
import de.dhbw.studienarbeit.WebView.components.DateTimePicker;
import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestFixedTime;
import de.dhbw.studienarbeit.data.reader.data.request.InvalidTimeSpanException;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.data.reader.data.station.OperatorName;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.web.data.Data;

@Route("analyseFixedTime")
public class AnalyseFixedTimeOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	private ComboBox<DelayStationData> stations;
	private MultiselectComboBox<Line> lines;
	private DateTimePicker begin;
	private DateTimePicker end;
	private Button btnSearch;

	private DelayRequestFixedTime request;

	private List<Component> filters = new ArrayList<>();

	private VerticalLayout layout;

	private Div divChart = new Div();

	@SuppressWarnings("rawtypes")
	public AnalyseFixedTimeOverview()
	{
		super();

		begin = new DateTimePicker(LocalTime.of(0, 0));
		begin.setLabel("von");
		end = new DateTimePicker(LocalTime.of(23, 59));
		end.setLabel("bis");

		btnSearch = new Button("Suchen", e -> search());
		btnSearch.setEnabled(false);

		lines = new MultiselectComboBox<>();
		lines.setLabel("Linien");
		lines.setItemLabelGenerator(item -> item.toString());
		filters.add(lines);

		filters.add(begin);
		filters.add(end);

		filters.forEach(e -> ((HasValueAndElement) e).setReadOnly(true));

		// Zum Testen:
		// DelayStationData test = new DelayStationData(new DelayMaximum(0), new
		// DelayAverage(0),
		// new StationID("de:08212:1"), new StationName("Test Marktplatz"), new
		// OperatorName("kvv"),
		// new Position(0, 0), 0);
		// stations = new ComboBox<>("Station", test);

		stations = new ComboBox<>("Station", Data.getDelaysStation());
		stations.setItemLabelGenerator(item -> item.getName().toString());
		stations.addValueChangeListener(e -> createRequest());

		HorizontalLayout filter = new HorizontalLayout(stations);
		filter.setHeight("100px");
		filter.setAlignItems(Alignment.CENTER);

		Div divLines = new Div(lines);
		divLines.setWidth("25%");

		filter.add(divLines, begin, end, btnSearch);

		layout = new VerticalLayout(filter);
		layout.setSizeFull();
		layout.setAlignItems(Alignment.STRETCH);
		divChart.setWidth("80%");
		layout.add(divChart);

		setContent(layout);
	}

	private void search()
	{
		end.setInvalid(false);
		try
		{
			request.setLines(lines.getValue());
			request.setTimestampStart(begin.getOptionalValue());
			request.setTimestampEnd(end.getOptionalValue());

			showDelays(request.getDelayCounts());
		}
		catch (InvalidTimeSpanException itse)
		{
			end.setErrorMessage("Ende darf nicht vor Beginn liegen");
			end.setInvalid(true);
		}
		catch (IOException e)
		{
			Notification.show("Fehler beim Suchen der Verspätungen");
		}
	}

	private void showDelays(List<DelayCountData> delays)
	{
		if (delays.isEmpty())
		{
			Notification.show("Ihre Suche ergibt keine Ergebnisse");
			return;
		}
		divChart.removeAll();
		divChart.add(AnalyseChart.getChartFor(delays));
	}

	@SuppressWarnings("rawtypes")
	private void createRequest()
	{
		if (!stations.getOptionalValue().isPresent())
		{
			filters.forEach(e -> ((HasValueAndElement) e).setReadOnly(true));
			btnSearch.setEnabled(false);
			return;
		}

		request = Data.getDelayRequestForFixedTimeFor(stations.getValue().getStationID());
		lines.setItems(Data.getLinesAt(stations.getValue().getStationID()));

		filters.forEach(e -> ((HasValueAndElement) e).setReadOnly(false));
		btnSearch.setEnabled(true);
	}
}
