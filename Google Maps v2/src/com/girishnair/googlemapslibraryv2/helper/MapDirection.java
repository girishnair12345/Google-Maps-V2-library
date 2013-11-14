package com.girishnair.googlemapslibraryv2.helper;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.util.Log;

public class MapDirection {
	
	public final static String DRIVING = "driving";
	public final static String WALKING = "walking";
	private final static String LOG_TAG = "MapDirection";
	
	public Document getXMLFromLatLong(LatLng firstLatLong, LatLng lastLatLong, String Mode) {
		String MapURL = "http://maps.googleapis.com/maps/api/directions/xml?"
				+ "origin=" + firstLatLong.latitude + "," + firstLatLong.longitude
				+ "&destination=" + lastLatLong.latitude + "," + lastLatLong.longitude
				+ "&sensor=false&units=metric&mode=driving";
		Log.i(LOG_TAG, "Map URL " + MapURL);
		try {
			Log.i(LOG_TAG, "Connection Started");
			HttpClient mHttpClient = new DefaultHttpClient();
			HttpResponse response = mHttpClient.execute(new HttpPost(MapURL), new BasicHttpContext());
			InputStream mInputStream = response.getEntity().getContent();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Log.i(LOG_TAG, "got Document");
			return builder.parse(mInputStream);
		} catch (Exception e) {
			Log.e(LOG_TAG, "Exception getXMLFromLatLong " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private String getDurDist(Document mDocument, String tagName, String type) {
		try {
			NodeList mNodeList = mDocument.getElementsByTagName(tagName);
			Node mNode = mNodeList.item(0);
			NodeList mNodeList2 = mNode.getChildNodes();
			Node mNode2 = mNodeList2.item(getNodeIndex(mNodeList2, type));
			Log.i(LOG_TAG ,"getDurDist " + mNode2.getTextContent());
			return mNode2.getTextContent();
		} catch (Exception e) {
			Log.i(LOG_TAG ,"getDurDist " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDurationText(Document mDocument) {
		return getDurDist(mDocument,"duration","text");
	}

	public String getDurationValue(Document mDocument) {
		return getDurDist(mDocument,"duration","value");
	}

	public String getDistanceText(Document mDocument) {
		return getDurDist(mDocument,"distance","text");
	}

	public String getDistanceValue(Document mDocument) {
		return getDurDist(mDocument,"distance","value");
	}

	private String getAddress(Document mDocument, String tagName) {
		try {
			NodeList nl1 = mDocument.getElementsByTagName(tagName);
			Node node1 = nl1.item(0);
			Log.i(LOG_TAG , "getAddress " + node1.getTextContent());
			return node1.getTextContent();
		} catch (Exception e) {
			Log.e(LOG_TAG , "Exception getAddress " + e.getMessage());
		}
		return null;
	}
	
	public String getFirstLatLongAddress(Document mDocument) {
		return getAddress(mDocument,"start_address");
	}

	public String getLastLatLongAddress(Document mDocument) {
		return getAddress(mDocument,"end_address");
	}


	public ArrayList<LatLng> getLatLongDirectionsList(Document mDocument) {
		ArrayList<LatLng> lstLantLong = new ArrayList<LatLng>();
		Log.d(LOG_TAG, "mDocument " + mDocument);
		NodeList mStepNodeList = mDocument.getElementsByTagName("step");
		if (mStepNodeList.getLength() > 0) {
			for (int i = 0; i < mStepNodeList.getLength(); i++) {
				Node mNode1 = mStepNodeList.item(i);
				NodeList mNodeList = mNode1.getChildNodes();
				Node locationNode = mNodeList.item(getNodeIndex(mNodeList, "start_location"));
				NodeList mNodeList2 = locationNode.getChildNodes();
				Node latNode = mNodeList2.item(getNodeIndex(mNodeList2, "lat"));
				double lat = Double.parseDouble(latNode.getTextContent());
				Node lngNode = mNodeList2.item(getNodeIndex(mNodeList2, "lng"));
				double lng = Double.parseDouble(lngNode.getTextContent());
				lstLantLong.add(new LatLng(lat, lng));

				locationNode = mNodeList.item(getNodeIndex(mNodeList, "polyline"));
				mNodeList2 = locationNode.getChildNodes();
				latNode = mNodeList2.item(getNodeIndex(mNodeList2, "points"));
				
				String txtNode = latNode.getTextContent();
				ArrayList<LatLng> arr = new ArrayList<LatLng>();
				int mIndex = 0;
				int mlatNodeLength = txtNode.length();
				int latitude = 0;
				int longitude = 0;
				while (mIndex < mlatNodeLength) {
					int b, shift = 0, result = 0;
					do {
						b = txtNode.charAt(mIndex++) - 63;
						result |= (b & 0x1f) << shift;
						shift += 5;
					} while (b >= 0x20);
					int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
					latitude += dlat;
					shift = 0;
					result = 0;
					do {
						b = txtNode.charAt(mIndex++) - 63;
						result |= (b & 0x1f) << shift;
						shift += 5;
					} while (b >= 0x20);
					int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
					longitude += dlng;

					LatLng position = new LatLng((double) latitude / 1E5, (double) longitude / 1E5);
					arr.add(position);
				}
				
				for (int j = 0; j < arr.size(); j++) {
					lstLantLong.add(new LatLng(arr.get(j).latitude, arr.get(j).longitude));
				}

				locationNode = mNodeList.item(getNodeIndex(mNodeList, "end_location"));
				mNodeList2 = locationNode.getChildNodes();
				latNode = mNodeList2.item(getNodeIndex(mNodeList2, "lat"));
				lat = Double.parseDouble(latNode.getTextContent());
				lngNode = mNodeList2.item(getNodeIndex(mNodeList2, "lng"));
				lng = Double.parseDouble(lngNode.getTextContent());
				lstLantLong.add(new LatLng(lat, lng));
			}
		}

		return lstLantLong;
	}

	private int getNodeIndex(NodeList nl, String nodename) {
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeName().equals(nodename))
				return i;
		}
		return -1;
	}
}