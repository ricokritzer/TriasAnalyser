package de.dhbw.studienarbeit.WebView.components;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;

@SuppressWarnings("serial")
public class DelayTab extends Tab
{
	private static final Logger LOGGER = Logger.getLogger(DelayTab.class.getName());

	private final TextField txtDelaySum = new TextField();
	private final TextField txtDelayAvg = new TextField();
	private final TextField txtDelayMax = new TextField();

	public DelayTab()
	{
		super();
		txtDelayMax.setLabel("Maximale Verspätung in Sekunden");
		txtDelayMax.setReadOnly(true);
		add(txtDelayMax);

		txtDelayAvg.setLabel("Durchschnittliche Verspätung in Sekunden");
		txtDelayAvg.setReadOnly(true);
		add(txtDelayAvg);

		txtDelaySum.setLabel("Verspätung in Summe in Sekunden");
		txtDelaySum.setReadOnly(true);
		add(txtDelaySum);

		add(new Button("aktualisieren", e -> setValuesDelay()));

		setValuesDelay();
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
}
