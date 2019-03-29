package de.dhbw.studienarbeit.WebView.tiles;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CountView extends Div
{
	private static final long serialVersionUID = 1L;

	public CountView(String what, CountData count)
	{
		final VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.add(createBorderlessHeadline(count.toString()));
		verticalLayout.add(createBorderlessHeadline(what));
		add(verticalLayout);
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
