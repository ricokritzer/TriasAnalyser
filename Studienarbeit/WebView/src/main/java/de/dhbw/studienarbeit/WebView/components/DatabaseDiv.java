package de.dhbw.studienarbeit.WebView.components;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTable;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableLine;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableWeather;

@SuppressWarnings("serial")
public class DatabaseDiv extends Div
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseDiv.class.getName());
	
	private final TextField txtCountStations = new TextField();
	private final TextField txtCountLines = new TextField();
	private final TextField txtCountStops = new TextField();
	private final TextField txtCountWeathers = new TextField();
	
	public DatabaseDiv()
	{
		super();
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
		
		add(new Button("aktualisieren", e -> setValues()));

		setValues();
		
		setVisible(false);
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
