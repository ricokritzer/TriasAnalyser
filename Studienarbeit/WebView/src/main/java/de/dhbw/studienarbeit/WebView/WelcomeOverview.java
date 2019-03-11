package de.dhbw.studienarbeit.WebView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.components.WelcomeDiv;

@Route("")
public class WelcomeOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public WelcomeOverview()
	{
		super();
		
		Component content = new Span(new WelcomeDiv());
		
		setContent(content);
	}
}
