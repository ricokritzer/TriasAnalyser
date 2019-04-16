package de.dhbw.studienarbeit.WebView.charts;

import java.io.IOException;
import java.util.Optional;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.request.Request;
import de.dhbw.studienarbeit.data.reader.data.request.RequestDB;
import de.dhbw.studienarbeit.data.reader.data.station.OperatorName;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationData;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;

public class AnalyseChartDialog extends Dialog
{
	private static final long serialVersionUID = 1L;

	private static final String ALL_STOPS = "alle Stops";
	private static final String FIXED_TIME = "fester Zeitpunkt";
	private static final String OVERVIEW = "Zeitraum";
	private String[] choices = { ALL_STOPS, FIXED_TIME, OVERVIEW };

	private ComboBox<StationData> cmbStations = new ComboBox<>("Station");
	private RadioButtonGroup<String> rbgOptions = new RadioButtonGroup<>();
	private MultiselectComboBox<LineName> cmbLines = new MultiselectComboBox<>();
	private Label lblError = new Label();

	private VerticalLayout layout = new VerticalLayout();

	public AnalyseChartDialog()
	{
		super();

		createStationsComboBox();
		createOptionsRadioButtons();
		createLinesComboBox();
		createErrorLabel();
		
		layout.setAlignItems(Alignment.STRETCH);
		add(layout);
	}

	private void createErrorLabel()
	{
		HorizontalLayout div = new HorizontalLayout(lblError);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);
		
		layout.add(div);
	}

	private void createLinesComboBox()
	{
		cmbLines.setLabel("Linien");
		cmbLines.setPlaceholder("Alle Linien");
		cmbLines.setItemLabelGenerator(e -> e.toString());
		cmbLines.setSizeFull();
		
		HorizontalLayout div = new HorizontalLayout(cmbLines);
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
		// stations.setItems(Data.getDelaysStation().stream().map(e ->
		// e.getValue()).collect(Collectors.toList()));
		cmbStations.setItems(new StationData(new StationID("de:08212:1"), new StationName("Marktplatz-Test"),
				new Position(0, 0), new OperatorName("KVV")));
		
		cmbStations.addValueChangeListener(e -> setLineItems());
		
		HorizontalLayout div = new HorizontalLayout(cmbStations);
		div.setSizeFull();
		div.setAlignItems(Alignment.STRETCH);
		
		layout.add(div);
	}

	private void setLineItems()
	{
		cmbLines.setReadOnly(false);
		Optional<StationData> value = cmbStations.getOptionalValue();
		if (value.isPresent())
		{
			Request request = new RequestDB(value.get());
			try
			{
				cmbLines.setItems(request.getPossibleLineNames());
				return;
			}
			catch (IOException e)
			{
				lblError.setText("An der ausgewählten Haltestelle halten keine Linien");
				return;
			}
		}
		
		lblError.setText("");
		cmbLines.setReadOnly(true);
	}
}
