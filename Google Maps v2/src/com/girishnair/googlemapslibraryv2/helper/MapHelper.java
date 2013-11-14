package com.girishnair.googlemapslibraryv2.helper;

import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.AZURE;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.BLUE;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.CYAN;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.GREEN;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.MAGENTA;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.NONE;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.ORANGE;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.RED;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.ROSE;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.VIOLET;
import static com.girishnair.googlemapslibraryv2.helper.MarkerColors.YELLOW;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapHelper {
	private GoogleMap googleMap;
	//private Activity mActivity;
	private byte MarkerColor;
	private final static String LOG_TAG = "MapHelper";
	private boolean moveCameraToLocation = true;
	private byte zoomLevel = 12;
	
	private float drawRoutePath_pathWidth = 3.0f;
	private int drawRoutePath_pathColor = Color.RED;
	private int Polygon_strokeColor = Color.RED;
	private int Polygon_fillColor = Color.BLUE;
	
	public MapHelper(GoogleMap googleMap) {
		this.googleMap = googleMap;
		
		MarkerColor = NONE;
	}

	/**
	 * Add a marker to Map
	 * @param latitude : Latitude 
	 * @param longitude : Longitude
	 * @param Message : The message to be shown
	 */
	public Marker addMarker(double latitude, double longitude, String Message) {
		MarkerOptions markerOptions = new MarkerOptions().position(
				new LatLng(latitude, longitude)).title(Message);
		if(moveCameraToLocation){
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(latitude, longitude)).zoom(zoomLevel).build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		}
		if(MarkerColor != NONE)
			getColor(markerOptions);
		return googleMap.addMarker(markerOptions);
	}

	public void zoomToLocation(double latitude, double longitude) {
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(zoomLevel)
				.build();
		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}
	public Marker addMarker(double latitude, double longitude , String Message,boolean moveCameraToLocation) {
		this.moveCameraToLocation = moveCameraToLocation;
		return addMarker(latitude,longitude,Message);
	}

	public Marker addMarker(double latitude, double longitude, String Message,boolean moveCameraToLocation, byte zoomLevel) {
		this.zoomLevel = zoomLevel;
		this.moveCameraToLocation = moveCameraToLocation;
		return addMarker(latitude, latitude,Message);
	}
	
	public Marker addMarker(double latitude, double longitude, String Message,boolean moveCameraToLocation, byte zoomLevel, Color color) {
		this.zoomLevel = zoomLevel;
		this.moveCameraToLocation = moveCameraToLocation;
		return addMarker(latitude, latitude,Message);
	}

	public void setMarkerColors(byte MarkerColors){
		this.MarkerColor = MarkerColors;
	}

	/**
	 * Set type of maps from NORMAL, HYBRID, SATELLITE or TERRAIN
	 * @param MapType : Check the MapType class
	 */
	public void setMapType(byte MapType) {
		switch (MapType) {
		case MapTypes.NORMAL:
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case MapTypes.HYBRID:
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case MapTypes.SATELLITE:
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case MapTypes.TERRAIN:
			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		default:
			googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;
		}
	}
	
	public void getColor(MarkerOptions marker) {
		switch (MarkerColor) {
		case AZURE:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
			break;
		case BLUE:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			break;
		case CYAN:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
			break;
		case GREEN:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			break;
		case MAGENTA:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
			break;
		case ORANGE:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
			break;
		case RED:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			break;
		case ROSE:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
			break;
		case VIOLET:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
			break;
		case YELLOW:
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
			break;

		default:
			MarkerColor = NONE;
			break;
		}
	}
	/**
	 * Set if you want your current location to be highlighted 
	 * @param enabled : Enable Current Location
	 */
	public void setCurrentLocation(boolean enabled){
		googleMap.setMyLocationEnabled(enabled); 
	}

	/**
	 * To enable the zoom controls (+/- buttons)
	 * @param enabled : Enable Zoom Controls
	 */
	public void setZoomControlsEnabled(boolean enabled) {
		googleMap.getUiSettings().setZoomControlsEnabled(enabled);
	}

	public void setZoomGesturesEnabled(boolean b) {
		googleMap.getUiSettings().setZoomGesturesEnabled(b);
	}

	public void setCompassEnabled(boolean b) {
		googleMap.getUiSettings().setCompassEnabled(b);
	}

	public void setMyLocationButtonEnabled(boolean b) {
		googleMap.getUiSettings().setMyLocationButtonEnabled(b);
	}

	public void setRotateGesturesEnabled(boolean b) {
		googleMap.getUiSettings().setRotateGesturesEnabled(b);
	}

	private void setCameraToLatLong(LatLng... args){
		LatLngBounds.Builder b = new LatLngBounds.Builder();
		for (LatLng m : args) {
		    b.include(m);
		}
		LatLngBounds bounds = b.build();
		
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25,25,5);
		googleMap.animateCamera(cu);
	}
	
	public void drawRoutePath(final Activity activity, final LatLng LatLng1, final LatLng LatLng2,final boolean moveCameraToPosition) {
		
		class DrawRoutePath extends AsyncTask<String, Void, Void>{
			private PolylineOptions mPolylineOptions;
			private ProgressDialog mProgressDialog;
			private MapDirection mMapDirection;
			private Document mDocument;
			@Override
			protected void onPreExecute() {
				if(activity != null)
				mProgressDialog = ProgressDialog.show(activity,"Fetching Direction","Please Wait...");
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(String... arg0) {
				try{
					mMapDirection = new MapDirection();
					mDocument = mMapDirection.getXMLFromLatLong(LatLng1,LatLng2, MapDirection.DRIVING);
				}catch (Exception e) {
					Log.e(LOG_TAG, "DrawRoutePath " + e.getMessage());
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				Log.d(LOG_TAG,"mDocument " + mDocument);
				ArrayList<LatLng> mLatLongList = mMapDirection.getLatLongDirectionsList(mDocument);
				mPolylineOptions = new PolylineOptions().width(drawRoutePath_pathWidth).color(drawRoutePath_pathColor);
				
				for (int i = 0; i < mLatLongList.size(); i++) {
					mPolylineOptions.add(mLatLongList.get(i));
				}
				 if(moveCameraToPosition)
					setCameraToLatLong(LatLng1,LatLng2);
				 if(activity != null)
			        mProgressDialog.cancel();	
				 googleMap.addPolyline(mPolylineOptions);
				super.onPostExecute(result);
			}
			   
		   }
		 new DrawRoutePath().execute("");
	}

	public Circle drawCircularRegion(LatLng LatLng,int radius, boolean moveCameraToPosition) {
		CircleOptions circleOptions = new CircleOptions()
	    .center(LatLng)
	    .radius(radius); 
		
	    Circle circle = googleMap.addCircle(circleOptions);
	    if(moveCameraToPosition)
			setCameraToLatLong(LatLng,getNewLatLongFromDistance(LatLng,radius));
	    return circle;
	}
	
	public Polygon drawPolygonRegion(boolean moveCameraToPosition, LatLng... LatLngs ) {
		PolygonOptions mPolygonOptions = new PolygonOptions();
		for(LatLng latlng: LatLngs)
			mPolygonOptions.add(latlng);
		mPolygonOptions.strokeColor(Polygon_strokeColor);
		mPolygonOptions.fillColor(Polygon_fillColor);
		if(moveCameraToPosition)
			zoomToFitLatLongs(LatLngs);
	    return googleMap.addPolygon(mPolygonOptions);
	}
	
	
	public void zoomToFitMarkers(Marker... markers){
		LatLngBounds.Builder b = new LatLngBounds.Builder();
		for (Marker m : markers) {
		    b.include(m.getPosition());
		}
		LatLngBounds bounds = b.build();
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25,25,5);
		googleMap.animateCamera(cu);
	}
	
	public void zoomToFitLatLongs(LatLng... latlng){
		LatLngBounds.Builder b = new LatLngBounds.Builder();
		for (LatLng l : latlng) {
		    b.include(l);
		}
		LatLngBounds bounds = b.build();
		CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25,25,5);
		googleMap.animateCamera(cu);
	}
	
	public double getDistanceBetweenLatLongs_Kilometers(LatLng latLng1, LatLng latLng2) {
		double theta = latLng1.longitude - latLng2.longitude;
		double dist = Math.sin((latLng1.latitude * Math.PI / 180.0))
				* Math.sin((latLng2.latitude * Math.PI / 180.0))
				+ Math.cos((latLng1.latitude * Math.PI / 180.0))
				* Math.cos((latLng2.latitude * Math.PI / 180.0))
				* Math.cos((theta * Math.PI / 180.0));
		dist = Math.acos(dist);
		dist = (dist * 180.0 / Math.PI);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		//in Kilometers
		return (dist);
	}
	
	public LatLng getNewLatLongFromDistance(LatLng latLng,float distance){
		distance = distance / 1000;//in meters
		double lat1 = Math.toRadians(latLng.latitude);
		double lon1 = Math.toRadians(latLng.longitude);
		
		double lat2 = Math.asin( Math.sin(lat1)*Math.cos(distance/6378.1) +
				Math.cos(lat1)*Math.sin(distance/6378.1)*Math.cos(1.57));
		double lon2 = lon1 + Math.atan2(Math.sin(1.57)*Math.sin(distance/6378.1)*Math.cos(lat1),
				Math.cos(distance/6378.1)-Math.sin(lat1)*Math.sin(lat2));
		lat2 = Math.toDegrees(lat2);
		lon2 = Math.toDegrees(lon2);
		return new LatLng(lat2, lon2);
	}

}
