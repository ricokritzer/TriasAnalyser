package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class HeatmapDiv extends Div
{
	private static final long serialVersionUID = 1L;

	public HeatmapDiv()
	{
		VerticalLayout layout = new VerticalLayout();

		layout.add(new Text("Folgt in Kürze ..."));

		// final LMap leafletMap = new LMap();
		// leafletMap.setCenter(60.4525, 22.301);
		// leafletMap.setZoomLevel(15.0);
		//
		// final LMarker m = new LMarker(60.4525, 22.301);
		// m.setDivIcon("Hello <strong>world</strong>!");
		// m.setIconSize(new Point(80, 20));
		//
		// final LMarker leafletMarker = new LMarker(60.4525, 22.301);
		// leafletMarker.setIconSize(new Point(57, 52));
		// leafletMarker.setIconAnchor(new Point(57, 26));
		// leafletMarker.setTitle("this is marker one!");
		// leafletMarker.setPopup("Hello <b>Vaadin World</b>!");
		//
		// final LeafletLayer l = new LGoogleLayer(Type.SATELLITE);
		//
		// leafletMap.addBaseLayer(l, "Google Maps layer");

		// layout.add(leafletMap);

		this.add(layout);
		setVisible(false);
	}
}
