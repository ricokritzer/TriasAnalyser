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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.communication.PushMode;

import de.dhbw.studienarbeit.WebView.data.CountBean;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import de.dhbw.studienarbeit.data.reader.database.Count;
import de.dhbw.studienarbeit.web.data.Data;

public class CountDiv extends Div
{
	private static final long serialVersionUID = 2L;

	private final TextField txtCountStations = new TextField();
	private final TextField txtCountLines = new TextField();
	private final TextField txtCountStops = new TextField();
	private final TextField txtCountWeathers = new TextField();
	private final TextField txtLastUpdate = new TextField();

	private final Grid<CountBean> grid = new Grid<>();

	public CountDiv()
	{
		super();

		VerticalLayout layout = new VerticalLayout();

		txtCountLines.setLabel("Anzahl der Linien");
		txtCountLines.setReadOnly(true);
		layout.add(txtCountLines);

		txtCountStations.setLabel("Anzahl der Stationen");
		txtCountStations.setReadOnly(true);
		layout.add(txtCountStations);

		txtCountStops.setLabel("Anzahl der Stops");
		txtCountStops.setReadOnly(true);
		layout.add(txtCountStops);

		txtCountWeathers.setLabel("Anzahl der Wettereinträge");
		txtCountWeathers.setReadOnly(true);
		layout.add(txtCountWeathers);

		txtLastUpdate.setLabel("Stand");
		txtLastUpdate.setReadOnly(true);
		layout.add(txtLastUpdate);

		grid.addColumn(db -> getStringOf(db.getCountLines())).setHeader("Linien").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountOperators())).setHeader("Verkehrsverbünde").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountStations())).setHeader("Stationen").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountStops())).setHeader("Stops").setSortable(false);
		grid.addColumn(db -> getStringOf(db.getCountWeathers())).setHeader("Wettereinträge").setSortable(false);
		grid.addColumn(db -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(db.getLastUpdate()))
				.setHeader("Zeitpunkt").setSortable(false);

		grid.setHeightByRows(true);
		grid.setSizeFull();

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
		final List<Date> countUpdates = Data.getCountUpdates();

		final List<CountBean> beans = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			final Count lines = Optional.ofNullable(countLines.get(i)).orElse(Count.UNABLE_TO_COUNT);
			final Count operators = Optional.ofNullable(countOperators.get(i)).orElse(Count.UNABLE_TO_COUNT);
			final Count stations = Optional.ofNullable(countStations.get(i)).orElse(Count.UNABLE_TO_COUNT);
			final Count stops = Optional.ofNullable(countStops.get(i)).orElse(Count.UNABLE_TO_COUNT);

			final Date updateDate = Optional.ofNullable(countUpdates.get(i)).orElse(new Date(0));

			beans.add(new CountBean(stations, lines, stops, Count.UNABLE_TO_COUNT, operators, updateDate));
		}

		return beans;
	}
}
