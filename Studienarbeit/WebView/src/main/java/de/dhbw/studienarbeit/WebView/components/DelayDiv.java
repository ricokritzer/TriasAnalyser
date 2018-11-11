package de.dhbw.studienarbeit.WebView.components;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;

@SuppressWarnings("serial")
public class DelayDiv extends Div
{
	private static final Logger LOGGER = Logger.getLogger(DelayDiv.class.getName());

	private final TextField txtDelaySum = new TextField();
	private final TextField txtDelayAvg = new TextField();
	private final TextField txtDelayMax = new TextField();

	public DelayDiv()
	{
		super();
		this.setTitle("Verspätungen");

		add(new Label("Alle Verspätungen in Sekunden"));

		txtDelayMax.setLabel("Maximal");
		txtDelayMax.setReadOnly(true);
		add(txtDelayMax);

		txtDelayAvg.setLabel("Durchschnitt");
		txtDelayAvg.setReadOnly(true);
		add(txtDelayAvg);

		txtDelaySum.setLabel("Summe");
		txtDelaySum.setReadOnly(true);
		add(txtDelaySum);

		add(new Button("aktualisieren", e -> setValuesDelay()));

		setValuesDelay();

		setVisible(false);
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
