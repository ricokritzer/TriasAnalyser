package de.dhbw.studienarbeit.WebView;

import java.io.IOException;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
	private Label lblCountStations = new Label();
	private Label lblCountLines = new Label();
	private Label lblCountStops = new Label();
	private Label lblCountWeathers = new Label();

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
		lblCountStations.setText(getCountOf(new DatabaseTableStation()));
		lblCountLines.setText(getCountOf(new DatabaseTableLine()));
		lblCountStops.setText(getCountOf(new DatabaseTableStop()));
		lblCountWeathers.setText(getCountOf(new DatabaseTableWeather()));
	}

	private String getCountOf(DatabaseTable table)
	{
		try
		{
			return Integer.toString(table.count());
		}
		catch (IOException e)
		{
			return "Die Anzahl konnte nicht berechnet werden.";
		}
	}
}
