package de.dhbw.studienarbeit.WebView;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.components.AboutDiv;
import de.dhbw.studienarbeit.WebView.components.CorrelationCoefficientsDiv;
import de.dhbw.studienarbeit.WebView.components.CountDiv;
import de.dhbw.studienarbeit.WebView.components.DelayCloudsDiv;
import de.dhbw.studienarbeit.WebView.components.DelayLineDiv;
import de.dhbw.studienarbeit.WebView.components.DelayStationDiv;
import de.dhbw.studienarbeit.WebView.components.DelayTemperatureDiv;
import de.dhbw.studienarbeit.WebView.components.DelayVehicleTypeDiv;
import de.dhbw.studienarbeit.WebView.components.DelayWeatherDiv;
import de.dhbw.studienarbeit.WebView.components.MapDiv;
import de.dhbw.studienarbeit.WebView.components.WelcomeDiv;

@Route("")
public class MainView extends VerticalLayout
{
	private static final long serialVersionUID = 1L;

	private final Tabs tabs = new Tabs();
	private final Map<Tab, Component> tabsToPages = new HashMap<>();

	public MainView()
	{
		setAlignItems(Alignment.CENTER);

		final WelcomeDiv divWelcome = new WelcomeDiv();
		final MapDiv mapDiv = new MapDiv();

		add(tabs);
		addTab("Willkommen", divWelcome);
		addTab("Unsere Daten", new CountDiv());
		addTab("Verspätungen nach Linien", new DelayLineDiv());
		addTab("Verspätungen nach Haltestelle", new DelayStationDiv());
		addTab("Verspätungen nach Wetter", new DelayWeatherDiv());
		addTab("Verspätungen nach Temperatur", new DelayTemperatureDiv());
		addTab("Verspätungen nach Bewölkung", new DelayCloudsDiv());
		addTab("Verspätungen nach Fahrzeugtyp", new DelayVehicleTypeDiv());
		addTab("Karte", mapDiv);
		addTab("Korrelationskoeffizienten", new CorrelationCoefficientsDiv());
		addTab("Über uns", new AboutDiv());
		tabs.setFlexGrowForEnclosedTabs(1);

		divWelcome.setVisible(true);

		tabs.addSelectedChangeListener(e -> {
			tabsToPages.values().forEach(div -> div.setVisible(false));
			tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
			if (tabsToPages.get(tabs.getSelectedTab()).equals(mapDiv))
			{
				mapDiv.initMap();
			}
		});
		
		setSizeFull();
	}

	private void addTab(String name, Div div)
	{
		final Tab tab = new Tab(name);
		tabs.add(tab);
		tabsToPages.put(tab, div);
		div.setVisible(false);
		add(div);
	}
}
