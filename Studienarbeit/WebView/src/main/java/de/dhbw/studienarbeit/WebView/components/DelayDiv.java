package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.shared.communication.PushMode;

import de.dhbw.studienarbeit.WebView.data.DelayBean;
import de.dhbw.studienarbeit.WebView.data.DelayDataProvider;

@PageTitle("Verspätungen")
public class DelayDiv extends Div
{
	private static final long serialVersionUID = 1L;

	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * 60;
	private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * 24;
	private static final int NUM_ROWS = 10;
	
	private final Grid<DelayBean> grid = new Grid<>();
	private List<DelayBean> delayItems = new ArrayList<>();

	private final TextField txtDelaySum = new TextField();
	private final TextField txtDelayAvg = new TextField();
	private final TextField txtDelayMax = new TextField();
	private final TextField txtLastUpdate = new TextField();

	private Binder<DelayBean> delayBinder = new Binder<>();

	public DelayDiv()
	{
		super();
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();

		delayBinder.forField(txtDelayMax).bind(db -> convertTimeToString(db.getMax()), null);
		delayBinder.forField(txtDelayAvg).bind(db -> convertTimeToString(db.getAvg()), null);
		delayBinder.forField(txtDelaySum).bind(db -> convertTimeToString(db.getSum()), null);
		delayBinder.forField(txtLastUpdate)
				.bind(db -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(db.getLastUpdate()), null);

		txtDelayMax.setLabel("Maximal");
		txtDelayMax.setReadOnly(true);
		layout.add(txtDelayMax);

		txtDelayAvg.setLabel("Durchschnitt");
		txtDelayAvg.setReadOnly(true);
		layout.add(txtDelayAvg);

		txtDelaySum.setLabel("Summe");
		txtDelaySum.setReadOnly(true);
		layout.add(txtDelaySum);

		txtLastUpdate.setLabel("Stand");
		txtLastUpdate.setReadOnly(true);
		layout.add(txtLastUpdate);
		
		grid.addColumn(db -> convertTimeToString(db.getMax())).setHeader("Maximal").setSortable(false);
		grid.addColumn(db -> convertTimeToString(db.getAvg())).setHeader("Durchschnitt").setSortable(false);
		grid.addColumn(db -> convertTimeToString(db.getSum())).setHeader("Summe").setSortable(false);
		grid.addColumn(db -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(db.getLastUpdate())).setSortable(false).setHeader("Stand");
		grid.setHeightByRows(true);
		grid.setSizeFull();

		layout.add(grid);
		
		add(layout);

		setVisible(false);
	}

	public void update(DelayBean bean)
	{
		addItem(bean);
		UI ui = getUI().orElse(UI.getCurrent());
		Optional.ofNullable(ui).ifPresent(currentUI -> currentUI.access(() -> {
			currentUI.getPushConfiguration().setPushMode(PushMode.MANUAL);
			delayBinder.readBean(bean);
			grid.setItems(delayItems);
			currentUI.push();
		}));
		DelayDataProvider.getInstance().readyForUpdate(this);
	}

	private void addItem(DelayBean bean)
	{
		delayItems.add(0, bean);
		while (delayItems.size() > NUM_ROWS)
		{
			delayItems.remove(delayItems.size() - 1);
		}
	}

	private String convertTimeToString(double time)
	{
		if (time > SECONDS_PER_DAY)
		{
			final double days = time / SECONDS_PER_DAY;
			return Double.toString(round(days)) + " Tage";
		}
		if (time > SECONDS_PER_HOUR)
		{
			final double hours = time / SECONDS_PER_HOUR;
			return Double.toString(round(hours)) + " Stunden";
		}
		if (time > SECONDS_PER_MINUTE)
		{
			final double minutes = time / SECONDS_PER_MINUTE;
			return Double.toString(round(minutes)) + " Minuten";
		}
		return Double.toString(time) + " Sekunden";
	}

	private double round(double value)
	{
		return Math.round(value * 100) / 100.0;
	}
}
