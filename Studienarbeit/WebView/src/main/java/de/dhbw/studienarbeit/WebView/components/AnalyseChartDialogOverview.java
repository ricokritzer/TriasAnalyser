package de.dhbw.studienarbeit.WebView.components;

import java.io.IOException;
import java.util.Set;

import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import de.dhbw.studienarbeit.WebView.overview.AnalyseChartDialogAllStops;
import de.dhbw.studienarbeit.WebView.overview.AnalyseOverview;
import de.dhbw.studienarbeit.WebView.requests.RequestGridData;
import de.dhbw.studienarbeit.data.reader.data.request.InvalidTimeSpanException;
import de.dhbw.studienarbeit.data.reader.data.request.Request;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class AnalyseChartDialogOverview extends AnalyseChartDialogAllStops
{
	private static final long serialVersionUID = 1L;

	public AnalyseChartDialogOverview(AnalyseOverview analyseOverview, Request request)
	{
		super(analyseOverview, request);
	}

	@Override
	protected void addData()
	{
		RequestGridData data;
		request.filterWeekdays(weekdays.getValue());
		try
		{
			request.filterStartHour(startHour.getValue());
			request.filterEndHour(endHour.getValue());
		}
		catch (InvalidTimeSpanException e)
		{
			lblError.setText(e.getMessage());
			return;
		}
		try
		{
			if (request.getDelays().isEmpty())
			{
				handleEmptyList();
			}
			data = new RequestGridData(request, analyseOverview.getNumDataset());
		}
		catch (IOException e)
		{
			handleIOException();
			return;
		}
		this.close();
		analyseOverview.addDataToGrid(data);
	}

	@Override
	protected void createFilters()
	{
		createWeekdayComboBox();
		createStartComboBox();
		createEndComboBox();
		createErrorLabel();
	}

	private void createErrorLabel()
	{
		HorizontalLayout div = new HorizontalLayout(lblError);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void createEndComboBox()
	{
		endHour.setPlaceholder("24 Uhr");
		endHour.setItemLabelGenerator(e -> e.toString());
		endHour.setSizeFull();
		endHour.setItems(Hour.values());

		HorizontalLayout div = new HorizontalLayout(endHour);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void createStartComboBox()
	{
		startHour.setPlaceholder("0 Uhr");
		startHour.setItemLabelGenerator(e -> e.toString());
		startHour.setSizeFull();
		startHour.setItems(Hour.values());

		HorizontalLayout div = new HorizontalLayout(startHour);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void createWeekdayComboBox()
	{
		weekdays.setLabel("Wochentage");
		weekdays.setPlaceholder("Alle Tage");
		weekdays.setItemLabelGenerator(e -> e.toString());
		weekdays.addValueChangeListener(e -> weekdaysChanged());
		weekdays.setSizeFull();
		weekdays.setItems(Weekday.values());

		HorizontalLayout div = new HorizontalLayout(weekdays);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void weekdaysChanged()
	{
		Set<Weekday> value = weekdays.getValue();
		if (value.isEmpty())
		{
			weekdays.setPlaceholder("Alle Tage");
		}
		else
		{
			weekdays.setPlaceholder("");
		}
		request.filterWeekdays(value);
	}
}
