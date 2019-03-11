package de.dhbw.studienarbeit.WebView.overview;

import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.StopAndWeatherCountChart;
import de.dhbw.studienarbeit.WebView.components.WelcomeDiv;

@Route("")
public class WelcomeOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public WelcomeOverview()
	{
		super();

		VerticalLayout textAreas = new VerticalLayout(new WelcomeDiv());
		textAreas.setAlignItems(Alignment.CENTER);
		textAreas.setWidth("25%");
		
		TextArea txtAbout = new TextArea();
		txtAbout.setLabel("Unsere Studienarbeit");
		txtAbout.setReadOnly(true);
		txtAbout.setValue("Patrick Siewert" + System.lineSeparator() + "Rico Kritzer");
		txtAbout.setSizeFull();
		textAreas.add(txtAbout);

		final TextArea txtImpress = new TextArea();
		txtImpress.setLabel("Impressum");
		txtImpress.setReadOnly(true);
		txtImpress.setValue(new StringBuilder() //
				.append("Duale Hochschule").append(System.lineSeparator()) //
				.append("Erzbergerstraße 121").append(System.lineSeparator()) //
				.append("76133 Karlsruhe").append(System.lineSeparator()) //
				.append("Deutschland") //
				.toString());
		txtImpress.setSizeFull();
		textAreas.add(txtImpress);

		VerticalLayout charts = new VerticalLayout(new StopAndWeatherCountChart());
		charts.setAlignItems(Alignment.STRETCH);
		charts.setWidth("75%");

		HorizontalLayout outer = new HorizontalLayout(textAreas, charts);
		outer.setAlignItems(Alignment.STRETCH);
		outer.setSizeFull();
		
		setContent(outer);
	}
}
