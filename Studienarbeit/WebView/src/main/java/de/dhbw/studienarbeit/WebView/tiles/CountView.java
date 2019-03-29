package de.dhbw.studienarbeit.WebView.tiles;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CountView extends Component
{
	public CountView(String what, CountData count)
	{
		// add(createBorderlessHeadline(count.toString()));
		// add(createBorderlessHeadline(what));
	}

	private H1 createBorderlessHeadline(String string)
	{
		final H1 h1 = new H1(string);
		h1.getStyle().set("border-size", "0");
		h1.getStyle().set("padding", "0");
		h1.getStyle().set("text-align", "center");
		return h1;
	}
}
