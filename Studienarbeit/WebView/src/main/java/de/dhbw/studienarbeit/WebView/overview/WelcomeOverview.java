package de.dhbw.studienarbeit.WebView.overview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.CountLineChart;
import de.dhbw.studienarbeit.web.data.Data;

@Route("")
public class WelcomeOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public WelcomeOverview()
	{
		VerticalLayout txtUnsereStudienarbeit = new VerticalLayout(new H1("Unsere Studienarbeit"),
				new Div(new Text("Patrick Siewert")), new Div(new Text("Rico Kritzer")));
		txtUnsereStudienarbeit.setAlignItems(Alignment.STRETCH);

		VerticalLayout txtImpressum = new VerticalLayout(new H1("Impressum"), new Div(new Text("Duale Hochschule")),
				new Div(new Text("Erzbergerstraße 121")), new Div(new Text("76133 Karlsruhe")),
				new Div(new Text("Deutschland")));
		txtImpressum.setAlignItems(Alignment.STRETCH);

		HorizontalLayout row1 = new HorizontalLayout(txtUnsereStudienarbeit,
				new CountLineChart(Data.getCountStopsLatest(), Data.getCountStops(), "Stops", "gespeichert"),
				new CountLineChart(Data.getCountWeathersLatest(), Data.getCountWeathers(), "Wettereinträge",
						"gespeichert"));
		row1.setSizeFull();
		row1.setAlignItems(Alignment.STRETCH);

		HorizontalLayout row2 = new HorizontalLayout(txtImpressum,
				new CountLineChart(Data.getCountLinesLatest(), Data.getCountLines(), "Linien", "gespeichert"),
				new CountLineChart(Data.getCountObservedStationsLatest(), Data.getCountObservedStations(),
						"Haltestellen", "unter Beobachtung"));
		row2.setSizeFull();
		row2.setAlignItems(Alignment.STRETCH);

		VerticalLayout content = new VerticalLayout(row1, row2);
		content.setMargin(false);
		content.setPadding(false);
		content.setSizeFull();

		setContent(content);
	}
}
