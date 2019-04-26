package de.dhbw.studienarbeit.WebView.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import de.dhbw.studienarbeit.WebView.overview.AnalyseOverview;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.request.Request;
import de.dhbw.studienarbeit.data.reader.data.request.RequestDB;
import de.dhbw.studienarbeit.data.reader.data.station.OperatorName;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationData;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;
import de.dhbw.studienarbeit.web.data.Data;

public class AnalyseChartDialog extends Dialog
{
	private static final long serialVersionUID = 1L;

	private static final String ALL_STOPS = "alle Stops";
	private static final String FIXED_TIME = "fester Zeitpunkt";
	private static final String OVERVIEW = "Zeitraum";
	private static String[] choices = { ALL_STOPS, FIXED_TIME, OVERVIEW };

	private AnalyseOverview analyseOverview;

	private ComboBox<StationData> cmbStations = new ComboBox<>("Station");
	private RadioButtonGroup<String> rbgOptions = new RadioButtonGroup<>();
	private Label lblError = new Label();
	private Button btnOk = new Button("Weiter");

	private VerticalLayout layout = new VerticalLayout();

	public AnalyseChartDialog(AnalyseOverview analyseOverview)
	{
		super();

		this.analyseOverview = analyseOverview;

		createStationsComboBox();
		createOptionsRadioButtons();
		createErrorLabel();
		createOkButton();

		layout.setAlignItems(Alignment.STRETCH);
		add(layout);
	}

	private void createOkButton()
	{
		btnOk.addClickListener(e -> nextDialog());

		HorizontalLayout div = new HorizontalLayout(btnOk);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void nextDialog()
	{
		Optional<StationData> value = cmbStations.getOptionalValue();
		if (!value.isPresent())
		{
			lblError.setText("Bitte wählen Sie eine Station aus");
			return;
		}

		Request request = new RequestDB(value.get());
		List<LineName> lineNames = new ArrayList<>();
		try
		{
			lineNames = request.getPossibleLineNames();
		}
		catch (IOException e)
		{
			lblError.setText("Verbindung zur Datenbank fehlgeschlagen");
			return;
		}

		if (lineNames.isEmpty())
		{
			lblError.setText("An der ausgewählten Haltestelle wurden keine Stops gespeichert");
			return;
		}

		Dialog dialog = null;
		if (rbgOptions.getValue().equals(ALL_STOPS))
		{
			dialog = new AnalyseChartDialogAllStops(analyseOverview, request);
		}
		else if (rbgOptions.getValue().equals(OVERVIEW))
		{
			dialog = new AnalyseChartDialogOverview(analyseOverview, request);
		}
		else if (rbgOptions.getValue().equals(FIXED_TIME))
		{
			dialog = new AnalyseChartDialogFixedTime(analyseOverview, request);
		}
		this.close();
		dialog.open();
	}

	private void createErrorLabel()
	{
		HorizontalLayout div = new HorizontalLayout(lblError);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void createOptionsRadioButtons()
	{
		rbgOptions.setItems(choices);
		rbgOptions.setValue(ALL_STOPS);

		HorizontalLayout div = new HorizontalLayout(rbgOptions);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}

	private void createStationsComboBox()
	{
		cmbStations.setItemLabelGenerator(e -> e.getName().toString());

		List<StationData> stations = Data.getDelaysStation().stream().map(e -> e.getValue())
				.collect(Collectors.toList());

		// zum Testen:
		if (stations.isEmpty())
		{
			stations.add(new StationData(new StationID("de:08212:1"), new StationName("Marktplatz-Test"),
					new Position(0, 0), new OperatorName("KVV")));
		}

		cmbStations.setItems(stations);
		cmbStations.addValueChangeListener(e -> lblError.setText(""));

		HorizontalLayout div = new HorizontalLayout(cmbStations);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);

		layout.add(div);
	}
}
