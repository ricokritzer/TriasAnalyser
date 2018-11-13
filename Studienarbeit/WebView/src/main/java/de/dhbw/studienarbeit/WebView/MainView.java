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
import de.dhbw.studienarbeit.WebView.components.DatabaseDiv;
import de.dhbw.studienarbeit.WebView.components.DelayDiv;
import de.dhbw.studienarbeit.WebView.components.HeatmapDiv;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
public class MainView extends VerticalLayout
{
	private static final long serialVersionUID = 4L;

	public MainView()
	{
		setWidth("100%");
		setAlignItems(Alignment.CENTER);

		Tab tabDatabase = new Tab("Unsere Daten");
		Tab tabDelay = new Tab("Verspätungen");
		Tab tabHeatmap = new Tab("Heatmap");
		Tab tabAbout = new Tab("Über uns");

		Tabs tabs = new Tabs(tabDatabase, tabDelay, tabHeatmap, tabAbout);

		Div divDatabase = new DatabaseDiv();
		Div divDelay = new DelayDiv();
		Div divAbout = new AboutDiv();
		Div divHeatmap = new HeatmapDiv();

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(tabDatabase, divDatabase);
		tabsToPages.put(tabDelay, divDelay);
		tabsToPages.put(tabAbout, divAbout);
		tabsToPages.put(tabHeatmap, divHeatmap);

		add(tabs);
		add(divDatabase);
		add(divDelay);
		add(divHeatmap);
		add(divAbout);

		divDatabase.setVisible(true);

		tabs.addSelectedChangeListener(e -> {
			tabsToPages.values().forEach(div -> div.setVisible(false));
			tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
		});
	}
}
