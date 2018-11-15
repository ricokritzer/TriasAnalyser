package de.dhbw.studienarbeit.WebView.components;

import java.text.SimpleDateFormat;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.communication.PushMode;

import de.dhbw.studienarbeit.WebView.data.DatabaseBean;
import de.dhbw.studienarbeit.WebView.data.DatabaseDataProvider;

public class DatabaseDiv extends Div
{
	private static final long serialVersionUID = 2L;

	private final TextField txtCountStations = new TextField();
	private final TextField txtCountLines = new TextField();
	private final TextField txtCountStops = new TextField();
	private final TextField txtCountWeathers = new TextField();
	private final TextField txtLastUpdate = new TextField();

	private Binder<DatabaseBean> databaseBinder = new Binder<>();

	public DatabaseDiv()
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

		databaseBinder.forField(txtCountLines).bind(db -> getStringOf(db.getCountLines()), null);
		databaseBinder.forField(txtCountStations).bind(db -> getStringOf(db.getCountStations()), null);
		databaseBinder.forField(txtCountStops).bind(db -> getStringOf(db.getCountStops()), null);
		databaseBinder.forField(txtCountWeathers).bind(db -> getStringOf(db.getCountWeather()), null);
		databaseBinder.forField(txtLastUpdate)
				.bind(db -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(db.getLastUpdate()), null);

		add(layout);

		DatabaseDataProvider.getInstance().getDataFor(this);

		setVisible(false);
	}

	private String getStringOf(int value)
	{
		return Integer.toString(value);
	}

	public void update(DatabaseBean bean)
	{
		getUI().ifPresent(ui -> ui.access(() -> {
			ui.getPushConfiguration().setPushMode(PushMode.MANUAL);
			databaseBinder.readBean(bean);
			ui.push();
		}));
		DatabaseDataProvider.getInstance().readyForUpdate(this);
	}
}
