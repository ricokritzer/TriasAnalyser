package de.dhbw.studienarbeit.WebView.components;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTable;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableLine;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableWeather;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

@SuppressWarnings("serial")
public class DatabaseDiv extends Div
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseDiv.class.getName());

	private static final long UPDATE_RATE_SECONDS = 60;

	private final TextField txtCountStations = new TextField();
	private final TextField txtCountLines = new TextField();
	private final TextField txtCountStops = new TextField();
	private final TextField txtCountWeathers = new TextField();
	private final TextField txtLastUpdate = new TextField();

	private static int countStation = 0;
	private static int countLines = 0;
	private static int countStops = 0;
	private static int countWeather = 0;
	private static Date lastUpdate = new Date();
	private static Timer timer;

	static
	{
		update();

		DatabaseDiv.timer = new Timer();
		timer.schedule(new MyTimerTask(DatabaseDiv::update), new Date(), UPDATE_RATE_SECONDS * 1000);
		LOGGER.log(Level.INFO, "Timer scheduled.");
	}

	public DatabaseDiv()
	{
		super();
		this.setTitle("Unsere Daten");

		final Timer reloadTimer = new Timer();
		reloadTimer.schedule(new MyTimerTask(this::setValues), new Date(), UPDATE_RATE_SECONDS * 1000);

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

		txtCountWeathers.setLabel("Anzahl der Wettereinträge");
		txtCountWeathers.setReadOnly(true);
		layout.add(txtCountWeathers);

		add(layout);

		setValues();

		setVisible(false);
	}

	private void setValues()
	{
		txtCountStations.setValue(getStringOf(countStation));
		txtCountLines.setValue(getStringOf(countLines));
		txtCountStops.setValue(getStringOf(countStops));
		txtCountWeathers.setValue(getStringOf(countWeather));
		txtLastUpdate.setValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(lastUpdate));
	}

	private String getStringOf(int value)
	{
		return Integer.toString(value);
	}

	private static int getCountOf(DatabaseTable table)
	{
		try
		{
			return table.count();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update.", e);
			return -1;
		}
	}

	private static void update()
	{
		countStation = getCountOf(new DatabaseTableStation());
		countLines = getCountOf(new DatabaseTableLine());
		countStops = getCountOf(new DatabaseTableStop());
		countWeather = getCountOf(new DatabaseTableWeather());
	}
}
