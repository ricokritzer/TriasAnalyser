package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;

public class AboutDiv extends Div
{
	private static final long serialVersionUID = 1L;

	public AboutDiv()
	{
		final TextField txtAbout = new TextField();
		txtAbout.setTitle("Unsere Studienarbeit");
		txtAbout.setEnabled(false);
		txtAbout.setValue("Patrick Siewert" + System.lineSeparator() + "Rico Kritzer");
		add(txtAbout);

		final TextField txtImpress = new TextField();
		txtImpress.setTitle("Impressum");
		txtImpress.setEnabled(false);
		txtImpress.setValue(new StringBuilder() //
				.append("Duale Hochschule Baden-Württemberg Karlsruhe").append(System.lineSeparator()) //
				.append("Erzbergerstraße 121").append(System.lineSeparator()) //
				.append("76133 Karlsruhe").append(System.lineSeparator()) //
				.append("Deutschland").append(System.lineSeparator()) //
				.toString());
		add(txtImpress);

		setVisible(false);
	}
}
