package de.dhbw.studienarbeit.WebView.overview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.router.Route;

@Route("analyse")
public class AnalyseOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public AnalyseOverview()
	{
		super();
		
		Component content = new Text("Comming soon...");
		setContent(content);
	}
}
