package de.dhbw.studienarbeit.WebView.overview;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasEnabled;
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
	private List<DelayCountData> delays;

	private List<Component> filters = new ArrayList<>();

	private boolean updating = false;

	private VerticalLayout layout;

	private Div div = new Div();

	public AnalyseOverview()
	{
		super();

		btnSearch = new Button("Suchen", e -> search());

		lines = new MultiselectComboBox<>();
		lines.setLabel("Linien");
		lines.setItemLabelGenerator(item -> item.getName().getValue() + " " + item.getDestination().getValue());
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

		filters.forEach(e -> ((HasEnabled) e).setEnabled(false));

		stations = new ComboBox<>("Station", Data.getDelaysStation());
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
		div.setWidth("80%");
		layout.add(div);

		setContent(layout);
	}

	private void search()
	{
		try
		{
			delays = request.getDelayCounts();
			showDelays();
		}
		catch (IOException e)
		{
			Notification.show("Fehler beim Suchen der Verspätungen");
		}
	}

	private void showDelays()
	{
		BarDataset dataset = new BarDataset().setData(getData()).setLabel("Verspätungen")
				.setBackgroundColor(Color.BLUE);

		BarData data = new BarData().addLabels(getLabels()).addDataset(dataset);

		BarOptions options = new BarOptions().setScales(new BarScale().setyAxes(getYAxis()));

		BarChart chart = new BarChart().setData(data).setOptions(options);

		ChartJs chartJS = new ChartJs(chart.toJson());

		div.removeAll();
		div.add(chartJS);
	}

	private String[] getLabels()
	{
		return delays.stream().map(e -> e.getDelay().toString()).toArray(String[]::new);
	}

	private BigDecimal[] getData()
	{
		return delays.stream().map(e -> BigDecimal.valueOf(e.getCountValue())).toArray(BigDecimal[]::new);
	}

	private List<YAxis<LinearTicks>> getYAxis()
	{
		List<YAxis<LinearTicks>> axis = new ArrayList<>();
		YAxis<LinearTicks> ticks = new YAxis<>();
		ticks.setType("logarithmic");
		axis.add(ticks);
		return axis;
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

	private void createRequest()
	{
		if (!stations.getOptionalValue().isPresent())
		{
			filters.forEach(e -> ((HasEnabled) e).setEnabled(false));
			return;
		}

		request = Data.getDelayRequestFor(stations.getValue().getStationID());
		lines.setItems(Data.getLinesAt(stations.getValue().getStationID()));

		filters.forEach(e -> ((HasEnabled) e).setEnabled(true));
	}
}
