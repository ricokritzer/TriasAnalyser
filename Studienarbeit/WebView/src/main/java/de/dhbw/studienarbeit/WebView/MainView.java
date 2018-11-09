package de.dhbw.studienarbeit.WebView;

import java.util.logging.Logger;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.components.DatabaseTab;
import de.dhbw.studienarbeit.WebView.components.DelayTab;

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route("")
public class MainView extends VerticalLayout
{
	private static final Logger LOGGER = Logger.getLogger(MainView.class.getName());

	public MainView()
	{
		Tabs tabs = new Tabs(new DatabaseTab(), new DelayTab());
		setWidth("100%");
		setAlignItems(Alignment.CENTER);

		add(tabs);
	}
}
