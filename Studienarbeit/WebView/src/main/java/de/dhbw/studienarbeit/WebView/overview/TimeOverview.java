package de.dhbw.studienarbeit.WebView.overview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.components.DelayHourDiv;
import de.dhbw.studienarbeit.WebView.components.DelayWeekdayDiv;

@Route("time")
public class TimeOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public TimeOverview()
	{
		super();

		Component content = new Div(new DelayHourDiv(), new DelayWeekdayDiv());
		setContent(content);
	}
}
