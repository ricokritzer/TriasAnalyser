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
import de.dhbw.studienarbeit.WebView.data.DatabaseDataProvider;
import de.dhbw.studienarbeit.WebView.data.DelayDataProvider;

@Route("")
public class MainView extends VerticalLayout
{
	private static final long serialVersionUID = 4L;

	private final Tabs tabs = new Tabs();
	private final Map<Tab, Component> tabsToPages = new HashMap<>();

	public MainView()
	{
		setWidth("100%");
		setAlignItems(Alignment.CENTER);

		final DelayDiv divDelay = new DelayDiv();
		final DatabaseDiv divDatabase = new DatabaseDiv();

		add(tabs);
		addTab("Unsere Daten", divDatabase);
		addTab("Verspätungen", divDelay);
		addTab("Heatmap", new HeatmapDiv());
		addTab("Über uns", new AboutDiv());

		divDatabase.setVisible(true);
		
		tabs.addSelectedChangeListener(e -> {
			tabsToPages.values().forEach(div -> div.setVisible(false));
			tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
		});
		
		DelayDataProvider.getInstance().getDataFor(divDelay);
		DelayDataProvider.getInstance().readyForUpdate(divDelay);
		DatabaseDataProvider.getInstance().getDataFor(divDatabase);
		DatabaseDataProvider.getInstance().readyForUpdate(divDatabase);
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
