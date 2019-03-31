package de.dhbw.studienarbeit.WebView.tiles;

import java.util.Arrays;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CountView extends Div
{
	private static final long serialVersionUID = 1L;

	public CountView(String what, CountData count)
	{
		final VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.add(createBorderlessHeadline(count.toString(), what));
		verticalLayout.setAlignItems(Alignment.CENTER);
		add(verticalLayout);
	}

	private H1 createBorderlessHeadline(String... strings)
	{
		final StringBuilder sb = new StringBuilder();
		Arrays.asList(strings).forEach(string -> sb.append(string).append(System.lineSeparator()));

		final H1 h1 = new H1(sb.toString());
		h1.getStyle().set("border-size", "0");
		h1.getStyle().set("padding", "0");
		return h1;
	}
}
