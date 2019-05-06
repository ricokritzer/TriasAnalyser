package de.dhbw.studienarbeit.WebView.overview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.components.DelayLineDiv;
import de.dhbw.studienarbeit.WebView.components.DelaySituationDiv;
import de.dhbw.studienarbeit.WebView.components.DelayStationDiv;
import de.dhbw.studienarbeit.WebView.components.DelayVehicleTypeDiv;

@Route("delay")
public class DelayOverview extends Overview
{
	private static final long serialVersionUID = 1L;
	
	public DelayOverview()
	{
		super();
		
		Component content = new Div(new DelayStationDiv(), new DelayLineDiv(), new DelayVehicleTypeDiv(), new DelaySituationDiv());
		setContent(content);
	}
}
