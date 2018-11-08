package de.dhbw.studienarbeit.WebView;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
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

	private final TextField lblCountStations = new TextField();
	private final TextField lblCountLines = new TextField();
	private final TextField lblCountStops = new TextField();
	private final TextField lblCountWeathers = new TextField();

	public MainView()
	{
		DatabaseTableStation dbtStation = new DatabaseTableStation();
		DatabaseTableLine dbtLine = new DatabaseTableLine();
		DatabaseTableStop dbtStop = new DatabaseTableStop();
		Text txtStation1 = new Text("");
		Text txtLine1 = new Text("");
		Text txtStop1 = new Text("");
		Text txtStation2 = new Text("");
		Text txtLine2 = new Text("");
		Text txtStop2 = new Text("");
		Text txtStation3 = new Text("");
		Text txtLine3 = new Text("");
		Text txtStop3 = new Text("");

		add(txtStation1);
		add(txtLine1);
		add(txtStop1);

		lblCountLines.setTitle("Anzahl der Linien");
		lblCountStations.setTitle("Anzahl der Stationen");
		lblCountStops.setTitle("Anzahl der Stops");
		lblCountWeathers.setTitle("Anzahl der Wettereinträge");

		add(lblCountLines);
		add(lblCountStations);
		add(lblCountStops);
		add(lblCountWeathers);

		setValues();

		add(new Button("aktualisieren", e -> setValues()));
	}

	private void setValues()
	{
		lblCountStations.setValue(getCountOf(new DatabaseTableStation()));
		lblCountLines.setValue(getCountOf(new DatabaseTableLine()));
		lblCountStops.setValue(getCountOf(new DatabaseTableStop()));
		lblCountWeathers.setValue(getCountOf(new DatabaseTableWeather()));

		final File file = new File(System.getProperty("user.home") + File.separator + "Studienarbeit" + File.separator
				+ "Configuration.conf");
		if (!file.canRead())
		{
			add(new Label("Datei kann nicht gelesen werden: " + file.getAbsolutePath()));
		}
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
