package de.dhbw.studienarbeit.WebView.overview;

import java.io.IOException;
import java.util.Set;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.mysql.cj.result.LocalTimeValueFactory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.dhbw.studienarbeit.WebView.components.DateTimePicker;
import de.dhbw.studienarbeit.WebView.requests.RequestGridData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.request.Request;
import de.dhbw.studienarbeit.data.reader.data.time.Hour;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;

public class AnalyseChartDialogAllStops extends Dialog
{
	private static final long serialVersionUID = 1L;

	protected Request request;
	protected AnalyseOverview analyseOverview;

	protected VerticalLayout layout = new VerticalLayout();

	protected MultiselectComboBox<LineName> cmbLines = new MultiselectComboBox<>();
	protected MultiselectComboBox<LineDestination> cmbLineDestinations = new MultiselectComboBox<>();
	protected MultiselectComboBox<Weekday> weekdays = new MultiselectComboBox<>();
	protected ComboBox<Hour> startHour = new ComboBox<>("von");
	protected ComboBox<Hour> endHour = new ComboBox<>("bis");
	protected DateTimePicker startDateTime = new DateTimePicker(new LocalTimeValueFactory().createFromTime(0, 0, 0, 0));
	protected DateTimePicker endDateTime = new DateTimePicker(new LocalTimeValueFactory().createFromTime(23, 59, 0, 0));
	protected Label lblError = new Label();
	private Button btnOk = new Button("OK");

	public AnalyseChartDialogAllStops(AnalyseOverview analyseOverview, Request request)
	{
		this.request = request;
		this.analyseOverview = analyseOverview;

		try
		{
			createLinesComboBox();
		}
		catch (IOException e)
		{
			handleIOException();
		}
		createLineDestinationsComboBox();
		createFilters();
		createOkButton();

		layout.setAlignItems(Alignment.STRETCH);
		add(layout);
	}

	private void createOkButton()
	{
		btnOk.addClickListener(e -> addData());

		HorizontalLayout div = new HorizontalLayout(btnOk);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	protected void addData()
	{
		RequestGridData data;
		try
		{
			if (request.getDelays().isEmpty())
			{
				handleEmptyList();
			}
			data = new RequestGridData(request, analyseOverview.getNumDataset());
		}
		catch (IOException e)
		{
			handleIOException();
			return;
		}
		this.close();
		analyseOverview.addDataToGrid(data);
	}

	protected void handleEmptyList()
	{
		this.close();
		Dialog error = new Dialog();
		error.add("Die Suche ergab keine Ergebnisse");
		error.open();
	}

	protected void createFilters()
	{
		// no further filters
	}

	private void createLineDestinationsComboBox()
	{
		cmbLineDestinations.setLabel("Richtung");
		cmbLineDestinations.setPlaceholder("Alle Richtungen");
		cmbLineDestinations.addValueChangeListener(e -> lineDestinationsChanged());
		cmbLineDestinations.setItemLabelGenerator(e -> e.toString());
		cmbLineDestinations.setSizeFull();

		HorizontalLayout div = new HorizontalLayout(cmbLineDestinations);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void lineDestinationsChanged()
	{
		Set<LineDestination> value = cmbLineDestinations.getValue();
		if (value.isEmpty())
		{
			cmbLineDestinations.setPlaceholder("Alle Richtungen");
		}
		else
		{
			cmbLineDestinations.setPlaceholder("");
		}
		request.filterLineDestination(value);
	}

	private void createLinesComboBox() throws IOException
	{
		cmbLines.setLabel("Linien");
		cmbLines.setPlaceholder("Alle Linien");
		cmbLines.addValueChangeListener(e -> linesChanged());
		cmbLines.setItemLabelGenerator(e -> e.toString());
		cmbLines.setSizeFull();
		cmbLines.setItems(request.getPossibleLineNames());

		HorizontalLayout div = new HorizontalLayout(cmbLines);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void linesChanged()
	{
		Set<LineName> value = cmbLines.getValue();
		if (value.isEmpty())
		{
			cmbLines.setPlaceholder("Alle Linien");
		}
		else
		{
			cmbLines.setPlaceholder("");
		}

		request.filterLineNames(value);
		try
		{
			cmbLineDestinations.setItems(request.getPossibleLineDestinations());
		}
		catch (IOException e)
		{
			handleIOException();
		}
	}

	protected void handleIOException()
	{
		this.close();
		Dialog error = new Dialog();
		error.add("Verbindung zur Datenbank fehlgeschlagen");
		error.open();
	}
}
