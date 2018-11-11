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

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route("")
public class MainView extends VerticalLayout
{
	public MainView()
	{
		setWidth("100%");
		setAlignItems(Alignment.CENTER);

		Tab tabDatabase = new Tab("Unsere Daten");
		Tab tabDelay = new Tab("Verspätungen");
		Tab tabAbout = new Tab("Über uns");

		Tabs tabs = new Tabs(tabDatabase, tabDelay, tabAbout);

		Div divDatabase = new DatabaseDiv();
		Div divDelay = new DelayDiv();
		Div divAbout = new AboutDiv();

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(tabDatabase, divDatabase);
		tabsToPages.put(tabDelay, divDelay);
		tabsToPages.put(tabAbout, divAbout);

		add(tabs);
		add(divDatabase);
		add(divDelay);
		add(divAbout);

		divDatabase.setVisible(true);

		tabs.addSelectedChangeListener(e -> {
			tabsToPages.values().forEach(div -> div.setVisible(false));
			tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
		});
	}
}
