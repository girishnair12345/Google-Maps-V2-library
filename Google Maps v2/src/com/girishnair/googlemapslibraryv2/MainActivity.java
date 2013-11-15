package com.girishnair.googlemapslibraryv2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.girishnair.googlemapslibraryv2.helper.LatLongLoadedListner;
import com.girishnair.googlemapslibraryv2.helper.MapHelper;
import com.girishnair.googlemapslibraryv2.helper.MapTypes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MainActivity extends Activity implements LatLongLoadedListner  {
	private GoogleMap googleMap;
	private ArrayList<LatLng> latlngs;
	MapHelper mapHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
        
        mapHelper = new MapHelper(googleMap);
       
        //To add a marker
        Marker marker = mapHelper.addMarker(17.385044, 78.486671,"Hello","World",false);
        
        //Set the map type
        mapHelper.setMapType(MapTypes.NORMAL);
        
        //Set if you want your current location to be highlighted 
        mapHelper.setCurrentLocation(true);
        
        //To enable the zoom controls (+/- buttons)
        mapHelper.setZoomControlsEnabled(true);
        
        //To enable gestures
        mapHelper.setZoomGesturesEnabled(true);
        
        //To enable compass
        mapHelper.setCompassEnabled(true);
        
        //Set to have a my location button which on clicked moves to your current location
        mapHelper.setMyLocationButtonEnabled(false);
        
        //To enable rotation in your map
        mapHelper.setRotateGesturesEnabled(true);
        
        //To draw a route path
        LatLng l1 = new LatLng  (46.952967f,-83.929158f);
        LatLng l2 = new LatLng  (35.952967f,-83.92545f);
        mapHelper.drawRoutePath(this,l1,l2,true);
        
        //To draw a Polygon
        mapHelper.drawPolygonRegion(true,l1,l2);
        
        //To draw a circular region
        mapHelper.drawCircularRegion(l1, 1000, true);
        
        //Add markers and pass those MarkerOptions and it will zoom to fit all MarkerOptions
        Marker m1 = mapHelper.addMarker(17.385044, 78.486671,"One","One Desc",false);
        Marker m2 = mapHelper.addMarker(12.385044, 48.486671,"Two","Two Desc",false);
        mapHelper.zoomToFitMarkers(m1,m2);
        
        //Pass the the LatLongs you have and it will zoom to fit all LatLongs
        LatLng latlong1 = new LatLng  (46.952967f,-83.929158f);
        LatLng latlong2 = new LatLng  (35.952967f,-83.92545f);
        mapHelper.zoomToFitLatLongs(latlong1,latlong2);
        
        //To get the distance between two LatLongs in kilometers
        mapHelper.getDistanceBetweenLatLongs_Kilometers(latlong1, latlong2);
        
        //To get the new LatLong from existing LatLong with a specific range distance
        mapHelper.getNewLatLongFromDistance(latlong1, 1000);
        
               
        Button btnAnimateMarker = (Button) findViewById(R.id.btnAnimateMarker);
        btnAnimateMarker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Add Marker with animation
		        mapHelper.addMarker(17.385044, 78.486671,"One","Desc",true);
		        
			}
		});
        
        Button btnPlayRouteAnimation = (Button) findViewById(R.id.btnPlayRouteAnimation);
        btnPlayRouteAnimation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Marker[] markers = new Marker[12];
				markers[0] = mapHelper.createMarker(17.305302,73.607342,"1", "One");
				markers[1] = mapHelper.createMarker(17.306111,73.603565,"2", "Two");
				markers[2] = mapHelper.createMarker(17.307924,73.600261,"3", "Three");
				markers[3] = mapHelper.createMarker(17.308846,73.599907,"4", "Four");
				markers[4] = mapHelper.createMarker(17.31185,73.597426,"5", "Five");
				markers[5] = mapHelper.createMarker(17.312941,73.595875,"6", "Six");
				markers[6] = mapHelper.createMarker(17.314508,73.593746,"7", "Seven");
				markers[7] = mapHelper.createMarker(17.315117,73.593263,"8", "Eight");
				markers[8] = mapHelper.createMarker(17.315286,73.59263,"9", "Nine");
				markers[9] = mapHelper.createMarker(17.315901,73.591144,"10", "Ten");
				markers[10] = mapHelper.createMarker(17.316142,73.589529,"11", "Eleven");
				markers[11] = mapHelper.createMarker(17.317939,73.586064,"12", "Twelve");
				
				mapHelper.PlayRouteAnimation(markers,2000,true);
			}
		}); 
       
    }
    
    public ArrayList<LatLng> getLatLongs(){
    	return latlngs;
    }
	@Override
	public void LatLongs(ArrayList<LatLng> latlngs) {
		if(latlngs != null){
			Toast.makeText(getApplicationContext(), "Lat Long received " + latlngs.size(), Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Lat Long is NULL", Toast.LENGTH_LONG).show();
		}
	}
   

}
