package de.dhbw.studienarbeit.data.trias;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLCreator
{
	public String getRequestXML(final String stopID)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(
				"<Trias version=\"1.1\" xmlns=\"http://www.vdv.de/trias\" xmlns:siri=\"http://www.siri.org.uk/siri\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		sb.append("<ServiceRequest>");
		sb.append("<siri:RequestorRef>7qzuxfx8EPvH</siri:RequestorRef>");
		sb.append("<RequestPayload>");
		sb.append("<StopEventRequest>");
		sb.append("<Location>");
		sb.append("<LocationRef>");
		sb.append("<StopPointRef>" + stopID + "</StopPointRef>");
		sb.append("</LocationRef>");
		sb.append("<DepArrTime>" + currentTime() + "</DepArrTime>");
		sb.append("</Location>");
		sb.append("<Params>");
		sb.append("<NumberOfResults>10</NumberOfResults>");
		sb.append("<StopEventType>departure</StopEventType>");
		sb.append("<IncludeRealtimeData>true</IncludeRealtimeData>");
		sb.append("</Params>");
		sb.append("</StopEventRequest>");
		sb.append("</RequestPayload>");
		sb.append("</ServiceRequest>");
		sb.append("</Trias>");

		return sb.toString();
	}

	private static String currentTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
	}
}
