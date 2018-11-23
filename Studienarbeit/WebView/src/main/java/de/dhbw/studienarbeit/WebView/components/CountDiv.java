package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.communication.PushMode;

import de.dhbw.studienarbeit.WebView.data.CountBean;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.database.Count;
import de.dhbw.studienarbeit.web.data.Data;

public class CountDiv extends Div
{
	private static final long serialVersionUID = 2L;

	private final Grid<CountBean> grid = new Grid<>();

	public CountDiv()
	{
		super();
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();

		grid.addColumn(db -> getStringOf(db.getCountLines())).setHeader("Linien").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountOperators())).setHeader("Verkehrsverbünde").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountStations())).setHeader("Stationen").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountStops())).setHeader("Stops").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountWeathers())).setHeader("Wettereinträge").setSortable(false);
		grid.addColumn(db -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(db.getLastUpdate()))
				.setHeader("Zeitpunkt").setSortable(false);

		grid.setHeightByRows(true);
		grid.setSizeFull();

		layout.add(grid);

		add(layout);

		setVisible(false);

		final Timer timer = new Timer();
		timer.schedule(new MyTimerTask(this::update), new Date(), 1000);
	}

	private String getStringOf(Count value)
	{
		if (value.equals(Count.UNABLE_TO_COUNT))
		{
			return "NaN";
		}
		return value.toString();
	}

	public void update()
	{
		UI ui = getUI().orElse(UI.getCurrent());
		Optional.ofNullable(ui).ifPresent(currentUI -> currentUI.access(() -> {
			currentUI.getPushConfiguration().setPushMode(PushMode.MANUAL);
			grid.setItems(getBeans());
			currentUI.push();
		}));
	}

	private List<CountBean> getBeans()
	{
		final List<Count> countLines = Data.getCountLines();
		final List<Count> countOperators = Data.getCountOperators();
		final List<Count> countStations = Data.getCountStations();
		final List<Count> countStops = Data.getCountStops();
		final List<Count> countWeathers = Data.getCountWeathers();
		final List<Date> countUpdates = Data.getCountUpdates();

		final List<CountBean> beans = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			final Count lines = getItem(countLines, i);
			final Count operators = getItem(countOperators, i);
			final Count stations = getItem(countStations, i);
			final Count stops = getItem(countStops, i);
			final Count weather = getItem(countWeathers, i);

			Date updateDate;
			if (i >= countUpdates.size())
			{
				updateDate = new Date(0);
			}
			else
			{
				
				updateDate = Optional.ofNullable(countUpdates.get(i)).orElse(new Date(0));
			}

			beans.add(new CountBean(stations, lines, stops, weather, operators, updateDate));
		}

		return beans;
	}

	private Count getItem(final List<Count> count, int i)
	{
		if (i >= count.size())
		{
			return Count.UNABLE_TO_COUNT;
		}
		return Optional.ofNullable(count.get(i)).orElse(Count.UNABLE_TO_COUNT);
	}
}
