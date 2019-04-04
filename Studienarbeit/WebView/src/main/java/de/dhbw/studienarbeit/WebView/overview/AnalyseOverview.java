package de.dhbw.studienarbeit.WebView.overview;

import java.io.IOException;
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
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayRequestTimespan;
import de.dhbw.studienarbeit.data.reader.data.request.InvalidTimeSpanException;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.web.data.Data;

@Route("analyse")
public class AnalyseOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	private ComboBox<DelayStationData> stations;
	private MultiselectComboBox<Line> lines;
	private MultiselectComboBox<Weekday> weekdays;
	private ComboBox<Hour> begin;
	private ComboBox<Hour> end;
	private Button btnSearch;

	private DelayRequestTimespan request;

	private List<Component> filters = new ArrayList<>();

	private boolean updating = false;

	private VerticalLayout layout;

	private Div divChart = new Div();

	@SuppressWarnings("rawtypes")
	public AnalyseOverview()
	{
		super();

		btnSearch = new Button("Suchen", e -> search());
		btnSearch.setEnabled(false);

		lines = new MultiselectComboBox<>();
		lines.setLabel("Linien");
		lines.setItemLabelGenerator(item -> item.toString());
		lines.addValueChangeListener(e -> setLine());
		filters.add(lines);

		weekdays = new MultiselectComboBox<>();
		weekdays.setItems(Weekday.values());
		weekdays.setLabel("Wochentage");
		weekdays.setItemLabelGenerator(Weekday::getName);
		weekdays.addValueChangeListener(e -> setWeekday());
		filters.add(weekdays);

		begin = new ComboBox<>("von", Hour.values());
		begin.setItemLabelGenerator(Hour::toString);
		begin.addValueChangeListener(e -> setHourBegin());
		filters.add(begin);

		end = new ComboBox<>("bis", Hour.values());
		end.setItemLabelGenerator(Hour::toString);
		end.addValueChangeListener(e -> setHourEnd());
		filters.add(end);

		filters.forEach(e -> ((HasValueAndElement) e).setReadOnly(true));

//		Zum Testen:
//		DelayStationData test = new DelayStationData(new DelayMaximum(0), new DelayAverage(0),
//				new StationID("de:08212:1"), new StationName("Test Marktplatz"), new OperatorName("kvv"),
//				new Position(0, 0), 0);
//		stations = new ComboBox<>("Station", test);

		stations = new ComboBox<>("Station", Data.getDelaysStation());
		stations.setItemLabelGenerator(item -> item.getName().toString());
		stations.addValueChangeListener(e -> createRequest());
		
		HorizontalLayout filter = new HorizontalLayout(stations);
		filter.setHeight("100px");
		filter.setAlignItems(Alignment.CENTER);
		
		Div divLines = new Div(lines);
		divLines.setWidth("25%");
		
		Div divWeekdays = new Div(weekdays);
		divWeekdays.setWidth("25%");
		
		filter.add(divLines, divWeekdays, begin, end, btnSearch);

		layout = new VerticalLayout(filter);
		layout.setSizeFull();
		layout.setAlignItems(Alignment.STRETCH);
		divChart.setWidth("80%");
		layout.add(divChart);

		setContent(layout);
	}

	private void search()
	{
		try
		{
			showDelays(request.getDelayCounts());
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

	private void setHourEnd()
	{
		if (updating)
		{
			return;
		}
		try
		{
			setPossibleBeginHours();

			request.setHourStart(end.getOptionalValue());
		}
		catch (InvalidTimeSpanException e)
		{
			// Exception fange ich schon vorher ab, Benutzer kann keine ungültige Zeit
			// angeben
		}
	}

	private void setPossibleBeginHours()
	{
		updating = true;
		Hour current = begin.getValue();
		if (!end.getOptionalValue().isPresent())
		{
			begin.setItems(Hour.values());
			begin.setValue(current);
			updating = false;
			return;
		}

		List<Hour> possibleHours = new ArrayList<>();
		Hour value = end.getValue();

		for (Hour hour : Hour.values())
		{
			if (hour.before(value))
			{
				possibleHours.add(hour);
			}
		}

		begin.setItems(possibleHours);
		begin.setValue(current);
		updating = false;
	}

	private void setHourBegin()
	{
		if (updating)
		{
			return;
		}
		try
		{
			setPossibleEndHours();

			request.setHourStart(begin.getOptionalValue());
		}
		catch (InvalidTimeSpanException e)
		{
			// Exception fange ich schon vorher ab, Benutzer kann keine ungültige Zeit
			// angeben
		}
	}

	private void setPossibleEndHours()
	{
		updating = true;
		Hour current = end.getValue();
		if (!begin.getOptionalValue().isPresent())
		{
			end.setItems(Hour.values());
			end.setValue(current);
			updating = false;
			return;
		}

		List<Hour> possibleHours = new ArrayList<>();
		Hour value = begin.getValue();

		for (Hour hour : Hour.values())
		{
			if (value.before(hour))
			{
				possibleHours.add(hour);
			}
		}

		end.setItems(possibleHours);
		end.setValue(current);
		updating = false;
	}

	private void setWeekday()
	{
		request.setWeekdays(weekdays.getValue());
	}

	private void setLine()
	{
		request.setLines(lines.getValue());
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

		request = Data.getDelayRequestFor(stations.getValue().getStationID());
		lines.setItems(Data.getLinesAt(stations.getValue().getStationID()));

		filters.forEach(e -> ((HasValueAndElement) e).setReadOnly(false));
		btnSearch.setEnabled(true);
	}
}
