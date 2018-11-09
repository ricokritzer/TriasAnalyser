package de.dhbw.studienarbeit.WebView;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
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

	private final TextField txtDelaySum = new TextField();
	private final TextField txtDelayAvg = new TextField();
	private final TextField txtDelayMax = new TextField();

	public MainView()
	{
		setWidth("100%");
		setAlignItems(Alignment.CENTER);

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

		add(new Button("aktualisieren", e -> setValuesDatabase()));

		txtDelayMax.setLabel("Maximale Verspätung in Sekunden");
		txtDelayMax.setReadOnly(true);
		add(txtDelayMax);

		txtDelayAvg.setLabel("Durchschnittliche Verspätung in Sekunden");
		txtDelayAvg.setReadOnly(true);
		add(txtDelayAvg);

		txtDelaySum.setLabel("Verspätung in Summe in Sekunden");
		txtDelaySum.setReadOnly(true);
		add(txtDelaySum);

		setValuesDelay();
		setValuesDatabase();

		add(new Button("aktualisieren", e -> setValuesDelay()));
	}

	private void setValuesDatabase()
	{
		txtCountStations.setValue(getCountOf(new DatabaseTableStation()));
		txtCountLines.setValue(getCountOf(new DatabaseTableLine()));
		txtCountStops.setValue(getCountOf(new DatabaseTableStop()));
		txtCountWeathers.setValue(getCountOf(new DatabaseTableWeather()));
	}

	private void setValuesDelay()
	{
		try
		{
			DatabaseTableStop stop = new DatabaseTableStop();
			Optional.ofNullable(stop.selectDelay().get(0)).ifPresent(this::setDelayValues);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to get delay data", e);
		}
	}

	private void setDelayValues(DelayDB delay)
	{
		txtDelayAvg.setValue(Double.toString(delay.getAverage()));
		txtDelaySum.setValue(Double.toString(delay.getSummary()));
		txtDelayMax.setValue(Double.toString(delay.getMaximum()));
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
