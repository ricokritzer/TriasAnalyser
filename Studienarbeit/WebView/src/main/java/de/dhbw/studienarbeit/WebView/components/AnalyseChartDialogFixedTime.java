package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.io.IOException;

import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

import de.dhbw.studienarbeit.WebView.overview.AnalyseOverview;
import de.dhbw.studienarbeit.WebView.requests.RequestGridData;
import de.dhbw.studienarbeit.data.reader.data.request.InvalidTimeSpanException;
import de.dhbw.studienarbeit.data.reader.data.request.Request;

public class AnalyseChartDialogFixedTime extends AnalyseChartDialogAllStops
{
	private static final long serialVersionUID = 1L;

	public AnalyseChartDialogFixedTime(AnalyseOverview analyseOverview, Request request)
	{
		super(analyseOverview, request);
	}

	@Override
	protected void createFilters()
	{
		createStartDateTimePicker();
		createEndDateTimePicker();
		createErrorLabel();
	}

	private void createErrorLabel()
	{
		HorizontalLayout div = new HorizontalLayout(lblError);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void createStartDateTimePicker()
	{
		startDateTime.setLabel("von");
		
		HorizontalLayout div = new HorizontalLayout(startDateTime);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void createEndDateTimePicker()
	{
		endDateTime.setLabel("bis");
		
		HorizontalLayout div = new HorizontalLayout(endDateTime);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	@Override
	protected void addData()
	{
		try
		{
			request.filterStartDate(startDateTime.getValue());
			request.filterEndDate(endDateTime.getValue());
		}
		catch (InvalidTimeSpanException e)
		{
			lblError.setText(e.getMessage());
		}

		RequestGridData data;
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
}
