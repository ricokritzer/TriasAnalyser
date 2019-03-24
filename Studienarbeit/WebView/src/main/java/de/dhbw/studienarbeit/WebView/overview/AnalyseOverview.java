package de.dhbw.studienarbeit.WebView.overview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.request.InvalidTimeSpanException;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.data.reader.data.station.OperatorName;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.web.data.request.DelayRequestWO;

@Route("analyse")
public class AnalyseOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	private ComboBox<DelayStationData> stations;
	private ComboBox<Line> lines;
	private ComboBox<Weekday> weekdays;
	private ComboBox<Hour> begin;
	private ComboBox<Hour> end;
	private Button btnSearch;

	private DelayRequestWO request;
	private List<Delay> delays;

	@SuppressWarnings("rawtypes")
	private List<ComboBox> filters = new ArrayList<>();

	private boolean updating = false;

	private VerticalLayout layout;

	public AnalyseOverview()
	{
		super();
		
		btnSearch = new Button("Suchen", e -> search());

		lines = new ComboBox<>("Linie");
		lines.setItemLabelGenerator(item -> item.getName().getValue() + " " + item.getDestination().getValue());
		lines.addValueChangeListener(e -> setLine());
		filters.add(lines);

		weekdays = new ComboBox<>("Wochentag", Weekday.values());
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

		filters.forEach(e -> e.setEnabled(false));
		
		stations = new ComboBox<>("Station",
				new DelayStationData(new DelayMaximum(10), new DelayAverage(5), new StationID("de:08212:1"),
						new StationName("test"), new OperatorName("test"), new Position(10, 10), 10));
		stations.setItemLabelGenerator(item -> item.getName().toString());
		stations.addValueChangeListener(e -> createRequest());

		HorizontalLayout filter = new HorizontalLayout(stations);
		filter.setHeight("100px");
		filter.setAlignItems(Alignment.STRETCH);
		filters.forEach(filter::add);
		filter.add(btnSearch);

		layout = new VerticalLayout(filter);
		layout.setSizeFull();
		layout.setAlignItems(Alignment.STRETCH);

		setContent(layout);
	}

	private void search()
	{
		try
		{
			delays = request.getDelays();
			showDelays();
		}
		catch (IOException e)
		{
			Notification.show("Fehler beim Suchen der Verspätungen");
		}
	}

	private void showDelays()
	{
		Collections.sort(delays);
		
		Map<Delay, Integer> delayCounts = new HashMap<>();
		Div div = new Div();
		
		for (Delay delay : delays)
		{
			if (delayCounts.containsKey(delay))
			{
				delayCounts.put(delay, delayCounts.get(delay) + 1);
			}
			else
			{
				delayCounts.put(delay, 1);
			}
		}
		
		List<Delay> sortedKeySet = asSortedList(delayCounts.keySet());
		
		BarDataset dataset = new BarDataset().setData(sortedData(sortedKeySet, delayCounts)).setLabel("Verspätungen").setBackgroundColor(Color.BLUE);
		
		BarData data = new BarData().addLabels(asSortedArray(delayCounts.keySet())).addDataset(dataset);
		
		BarOptions options = new BarOptions().setScales(new BarScale().setyAxes(getYAxis()));
		
		BarChart chart = new BarChart().setData(data).setOptions(options);
		
		ChartJs chartJS = new ChartJs(chart.toJson());
		
		div.add(chartJS);
		
		layout.add(chartJS);
	}

	private List<YAxis<LinearTicks>> getYAxis()
	{
		List<YAxis<LinearTicks>> axis = new ArrayList<>();
		YAxis<LinearTicks> ticks = new YAxis<>();
		ticks.setType("logarithmic");
		axis.add(ticks);
		return axis;
	}

	private int[] sortedData(List<Delay> sortedKeySet, Map<Delay, Integer> delayCounts)
	{
		int[] data = new int[sortedKeySet.size()];
		int i = 0;
		for (Delay delay : sortedKeySet)
		{
			data[i] = delayCounts.get(delay);
			i++;
		}
		return data;
	}

	private List<Delay> asSortedList(Set<Delay> keySet)
	{
		List<Delay> list = new ArrayList<>(keySet);
		Collections.sort(list);
		return list;
	}

	private String[] asSortedArray(Set<Delay> keySet)
	{
		return asSortedList(keySet).stream().map(e -> e.toString()).toArray(String[]::new);
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
		request.setWeekday(weekdays.getOptionalValue());
	}

	private void setLine()
	{
		if (lines.getOptionalValue().isPresent())
		{
			request.setLineID(lines.getValue().getID());
		}
		else
		{
			request.setLineID(Optional.empty());
		}
	}

	private void createRequest()
	{
		if (!stations.getOptionalValue().isPresent())
		{
			filters.forEach(e -> e.setEnabled(false));
			return;
		}

		request = new DelayRequestWO(stations.getValue().getStationID());
		try
		{
			lines.setItems(request.getLines());
		}
		catch (IOException exc)
		{
			Notification
					.show("Haltestelle " + stations.getValue().getName().toString() + " wird von keiner Linie bedient");
			filters.forEach(e -> e.setEnabled(false));
			return;
		}

		filters.forEach(e -> e.setEnabled(true));
	}
}
