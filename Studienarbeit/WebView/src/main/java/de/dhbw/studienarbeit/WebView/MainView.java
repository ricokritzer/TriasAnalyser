package de.dhbw.studienarbeit.WebView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTable;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableLine;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableWeather;

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route("")
public class MainView extends VerticalLayout
{
	private static final Logger LOGGER = Logger.getLogger(MainView.class.getName());

	private final TextField txtCountStations = new TextField();
	private final TextField txtCountLines = new TextField();
	private final TextField txtCountStops = new TextField();
	private final TextField txtCountWeathers = new TextField();

	public MainView()
	{
		add(new Text("Unsere Daten im Testsystem"));

		txtCountLines.setLabel("Anzahl der Linien");
		txtCountLines.setReadOnly(true);
		add(txtCountLines);

		txtCountStations.setLabel("Anzahl der Stationen");
		txtCountStations.setReadOnly(true);
		add(txtCountStations);

		txtCountStops.setLabel("Anzahl der Stops");
		txtCountStops.setReadOnly(true);
		add(txtCountStops);

		txtCountWeathers.setLabel("Anzahl der Wettereinträge");
		txtCountWeathers.setReadOnly(true);
		add(txtCountWeathers);

		setValues();

		add(new Button("aktualisieren", e -> setValues()));
	}

	private void setValues()
	{
		txtCountStations.setValue(getCountOf(new DatabaseTableStation()));
		txtCountLines.setValue(getCountOf(new DatabaseTableLine()));
		txtCountStops.setValue(getCountOf(new DatabaseTableStop()));
		txtCountWeathers.setValue(getCountOf(new DatabaseTableWeather()));
	}

	private String getCountOf(DatabaseTable table)
	{
		try
		{
			return Integer.toString(table.count());
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count data.", e);
			return "Die Anzahl konnte nicht berechnet werden.";
		}
	}
}
