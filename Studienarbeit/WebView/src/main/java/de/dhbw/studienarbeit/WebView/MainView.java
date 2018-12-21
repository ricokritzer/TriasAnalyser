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
import de.dhbw.studienarbeit.WebView.components.CountDiv;
import de.dhbw.studienarbeit.WebView.components.DelayCloudsDiv;
import de.dhbw.studienarbeit.WebView.components.DelayLineDiv;
import de.dhbw.studienarbeit.WebView.components.DelayStationDiv;
import de.dhbw.studienarbeit.WebView.components.DelayTemperatureDiv;
import de.dhbw.studienarbeit.WebView.components.DelayWeatherDiv;
import de.dhbw.studienarbeit.WebView.components.WelcomeDiv;

@Route("")
public class MainView extends VerticalLayout
{
	private static final long serialVersionUID = 4L;

	private final Tabs tabs = new Tabs();
	private final Map<Tab, Component> tabsToPages = new HashMap<>();

	public MainView()
	{
		setAlignItems(Alignment.CENTER);

		final WelcomeDiv divWelcome = new WelcomeDiv();

		add(tabs);
		addTab("Willkommen", divWelcome);
		addTab("Unsere Daten", new CountDiv());
		addTab("Verspätungen nach Linien", new DelayLineDiv());
		addTab("Verspätungen nach Haltestelle", new DelayStationDiv());
		addTab("Verspätungen nach Wetter", new DelayWeatherDiv());
		addTab("Verspätungen nach Temperatur", new DelayTemperatureDiv());
		addTab("Verspätungen nach Bewölkung", new DelayCloudsDiv());
		addTab("Über uns", new AboutDiv());
		tabs.setFlexGrowForEnclosedTabs(1);

		divWelcome.setVisible(true);

		tabs.addSelectedChangeListener(e -> {
			tabsToPages.values().forEach(div -> div.setVisible(false));
			tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
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
