package de.dhbw.studienarbeit.WebView;

import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.components.WelcomeDiv;

@Route("")
public class Welcome extends Overview
{
	private static final long serialVersionUID = 1L;

	public Welcome()
	{
		super();
		
		setContent(new WelcomeDiv());
	}
}
