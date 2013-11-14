package com.girishnair.googlemapslibraryv2;

import android.app.Activity;
import android.os.Bundle;

import com.girishnair.googlemapslibraryv2.R;
import com.girishnair.googlemapslibraryv2.helper.MapHelper;
import com.girishnair.googlemapslibraryv2.helper.MapTypes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MainActivity extends Activity  {
	private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
        MapHelper mapHelper = new MapHelper(googleMap);
        //To add a marker
        Marker marker = mapHelper.addMarker(17.385044, 78.486671,"Hello");
        
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
        Marker m1 = mapHelper.addMarker(17.385044, 78.486671,"One");
        Marker m2 = mapHelper.addMarker(12.385044, 48.486671,"Two");
        mapHelper.zoomToFitMarkers(m1,m2);
        
        //Pass the the LatLongs you have and it will zoom to fit all LatLongs
        LatLng latlong1 = new LatLng  (46.952967f,-83.929158f);
        LatLng latlong2 = new LatLng  (35.952967f,-83.92545f);
        mapHelper.zoomToFitLatLongs(latlong1,latlong2);
        
        //To get the distance between two LatLongs in kilometers
        mapHelper.getDistanceBetweenLatLongs_Kilometers(latlong1, latlong2);
        
        //To get the new LatLong from existing LatLong with a specific range distance
        mapHelper.getNewLatLongFromDistance(latlong1, 1000);
       
    }

	

}
