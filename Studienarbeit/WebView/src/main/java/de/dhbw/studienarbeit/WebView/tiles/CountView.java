package de.dhbw.studienarbeit.WebView.tiles;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.dom.ElementFactory;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CountView extends Div
{
	private static final long serialVersionUID = 1L;

	public CountView(String what, CountData count)
	{
		setSizeFull();
		H1 text = new H1();
		text.add(count.toString());
		text.getElement().appendChild(ElementFactory.createBr());
		text.add(what);
		text.getStyle().set("text-align", "center");
		add(text);
	}
}
