package de.dhbw.studienarbeit.WebView;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

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

		Tab tabDatabase = new Tab("Database");
		Tab tabDelay = new Tab("Delay");
		Tabs tabs = new Tabs(tabDatabase, tabDelay);

		Div divDatabase = new DatabaseDiv();
		Div divDelay = new DelayDiv();

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(tabDatabase, divDatabase);
		tabsToPages.put(tabDelay, divDelay);

		add(tabs);
		add(divDatabase);
		add(divDelay);
		
		divDatabase.setVisible(true);

		tabs.addSelectedChangeListener(e -> {
			for (Component div : tabsToPages.values())
			{
				div.setVisible(false);
			}
			
			tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
		});
	}
}
