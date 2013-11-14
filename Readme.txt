The new Google Maps has been evolved as a better version than its predecessor. Here is the simple implementation of the Google maps version 2.0 

On Windows
keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android

On Linux or Mac OS
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android


Under Certificate fingerprints copy the SHA1 which would look like this

F2:10:B2:4C:2D:E5:FF:2X:99:C4:97:25:36:76:6E:F2:27:62:9B:22

Now goto
https://cloud.google.com/console#/project and click Create Project
 
Enter name of the project and add the project ID
On the left select APIs & auth and then APIs
Select Google Maps Android API v2 and click Off button to switch it on
Now on the right select Registered apps
Enter a name and select Android and select Accessing APIs directly from Android device

Enter your pacakge name and SHA1 Fingerprint
Under Android Key tab select API Key, It would look something like this
AIzaSyCQIh4aWi8DonvB9FCIKRvGFQ9guqo-Xr8

Now in the manifest add these lines
	<uses-sdk
        android:minSdkVersion="11"
        android:targetSdkVersion="11" />

    <permission
        android:name="com.girishnair.googlemapslibraryv2.permission.MAPS_RECEIVE"
        android:protectionLevel="signature" />

    <uses-permission android:name="com.girishnair.googlemapslibraryv2.permission.MAPS_RECEIVE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-feature
        android:glEsVersion="0x00020000"
        android:required="true" />
		
and under the application tag add these lines

	<meta-data
        android:name="com.google.android.gms.version"
        android:value="4030500" />
    <meta-data
        android:name="com.google.android.maps.v2.API_KEY"
        android:value="AIzaSyCQIh4aWi8DonvB9FCIKRvGFQ9guqo-Xr8" /> // Your key should go here
		
How to use

Create and instance of your map like this
	private GoogleMap googleMap;
	if (googleMap == null) {
		googleMap = ((MapFragment) getFragmentManager()
			.findFragmentById(R.id.map)).getMap();
	}
Now we can proceed
Create an instance of our helper class
	MapHelper mapHelper = new MapHelper(googleMap);
        //To add a marker
        MarkerOptions markerOptions = mapHelper.addMarker(17.385044, 78.486671,"Hello");
        
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
        MarkerOptions m1 = mapHelper.addMarker(17.385044, 78.486671,"One");
        MarkerOptions m2 = mapHelper.addMarker(12.385044, 48.486671,"Two");
        mapHelper.zoomToFitMarkers(m1,m2);
        
        //Pass the the LatLongs you have and it will zoom to fit all LatLongs
        LatLng latlong1 = new LatLng  (46.952967f,-83.929158f);
        LatLng latlong2 = new LatLng  (35.952967f,-83.92545f);
        mapHelper.zoomToFitLatLongs(latlong1,latlong2);
        
        //To get the distance between two LatLongs in kilometers
        mapHelper.getDistanceBetweenLatLongs_Kilometers(latlong1, latlong2);
        
        //To get the new LatLong from existing LatLong with a specific range distance
        mapHelper.getNewLatLongFromDistance(latlong1, 1000);