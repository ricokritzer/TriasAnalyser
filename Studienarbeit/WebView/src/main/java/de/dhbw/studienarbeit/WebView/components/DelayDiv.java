package de.dhbw.studienarbeit.WebView.components;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

@SuppressWarnings("serial")
public class DelayDiv extends Div
{
	private static final Logger LOGGER = Logger.getLogger(DelayDiv.class.getName());

	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * 60;
	private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * 24;

	private final TextField txtDelaySum = new TextField();
	private final TextField txtDelayAvg = new TextField();
	private final TextField txtDelayMax = new TextField();

	private static DelayDB delay;
	private static Timer timer;

	public DelayDiv()
	{
		super();
		this.setTitle("Verspätungen");

		if (!Optional.ofNullable(delay).isPresent())
		{
			update();
		}
		if (!Optional.ofNullable(timer).isPresent())
		{
			setTimer();
		}

		HorizontalLayout layout = new HorizontalLayout();

		txtDelayMax.setLabel("Maximal");
		txtDelayMax.setReadOnly(true);
		layout.add(txtDelayMax);

		txtDelayAvg.setLabel("Durchschnitt");
		txtDelayAvg.setReadOnly(true);
		layout.add(txtDelayAvg);

		txtDelaySum.setLabel("Summe");
		txtDelaySum.setReadOnly(true);
		layout.add(txtDelaySum);

		add(layout);

		setDelayValues();

		setVisible(false);
	}

	private static void update()
	{
		try
		{
			DatabaseTableStop stop = new DatabaseTableStop();
			Optional.ofNullable(stop.selectDelay().get(0)).ifPresent(DelayDiv::setDelay);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to get delay data", e);
		}
	}

	private static void setDelay(DelayDB delay)
	{
		DelayDiv.delay = delay;
	}

	private static void setTimer()
	{
		DelayDiv.timer = new Timer();
		timer.schedule(new MyTimerTask(DelayDiv::update), 60l * 1000);
	}

	private void setDelayValues()
	{
		txtDelayAvg.setValue(convertTimeToString(delay.getAverage()));
		txtDelaySum.setValue(convertTimeToString(delay.getSummary()));
		txtDelayMax.setValue(convertTimeToString(delay.getMaximum()));
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
