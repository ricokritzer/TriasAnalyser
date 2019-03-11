package de.dhbw.studienarbeit.WebView.charts;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import be.ceau.chart.LineChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.LineData;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.options.LineOptions;
import de.dhbw.studienarbeit.web.data.Data;

public class StationCountChart extends AbstractChart
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getChart()
	{
		LineDataset datasetStation = new LineDataset().setData(getStationData()).setLabel("Stationen").setBorderColor(Color.BLUE).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		LineDataset datasetObserved = new LineDataset().setData(getObservedData()).setLabel("beobachtete Stationen").setBorderColor(Color.RED).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		LineDataset datasetRealtime = new LineDataset().setData(getRealTimeData()).setLabel("Stationen mit Echtzeitdaten").setBorderColor(Color.GREEN).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		
		LineData data = new LineData().addDataset(datasetStation).addDataset(datasetObserved).addDataset(datasetRealtime).addLabels(getLabels());
		
		LineOptions options = new LineOptions().setResponsive(true);
		
		return new LineChart(data, options).toJson();
	}

	private List<BigDecimal> getStationData()
	{
		return Data.getCountsWO().getData().stream().map(e -> BigDecimal.valueOf(e.getCountStations().getValue())).collect(Collectors.toList());
	}

	private List<BigDecimal> getRealTimeData()
	{
		return Data.getCountsWO().getData().stream().map(e -> BigDecimal.valueOf(e.getCountStationsWithRealtimeData().getValue())).collect(Collectors.toList());
	}

	private List<BigDecimal> getObservedData()
	{
		return Data.getCountsWO().getData().stream().map(e -> BigDecimal.valueOf(e.getCountObservedStations().getValue())).collect(Collectors.toList());
	}
	
	private String[] getLabels()
	{
		return Data.getCountsWO().getData().stream().map(e -> new SimpleDateFormat("HH:mm:ss").format(e.getLastUpdate())).toArray(String[]::new);
	}
}
