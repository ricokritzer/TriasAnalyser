package de.dhbw.studienarbeit.WebView.overview;

import java.io.IOException;
import java.util.Set;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.dhbw.studienarbeit.WebView.requests.RequestGridData;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.request.Request;

public class AnalyseChartDialogAllStops extends Dialog
{
	private static final long serialVersionUID = 1L;

	private Request request;

	private VerticalLayout layout = new VerticalLayout();

	private MultiselectComboBox<LineName> cmbLines = new MultiselectComboBox<>();
	private MultiselectComboBox<LineDestination> cmbLineDestinations = new MultiselectComboBox<>();
	private Button btnOk = new Button("OK");

	public AnalyseChartDialogAllStops(Request request)
	{
		this.request = request;

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

	private void addData()
	{
		RequestGridData data;
		try
		{
			data = new RequestGridData(request, AnalyseOverview.numDataset);
		}
		catch (IOException e)
		{
			handleIOException();
			return;
		}
		this.close();
		AnalyseOverview.addDataToGrid(data);
	}

	private void createFilters()
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

	private void handleIOException()
	{
		this.close();
		Dialog error = new Dialog();
		error.add("Verbindung zur Datenbank fehlgeschlagen");
		error.open();
	}
}
