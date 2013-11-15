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
import android.graphics.Interpolator;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.Projection;
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
	private byte MarkerColor;
	private final static String LOG_TAG = "MapHelper";
	private boolean moveCameraToLocation = true;
	private byte zoomLevel = 12;
	private float drawRoutePath_pathWidth = 3.0f;
	private int drawRoutePath_pathColor = Color.RED;
	private int Polygon_strokeColor = Color.RED;
	private int Polygon_fillColor = Color.BLUE;
	private CancelableCallback mCancelableCallback;
	private int currentMarkerPosition;

	public MapHelper(GoogleMap googleMap) {
		this.googleMap = googleMap;

		MarkerColor = NONE;
	}

	/**
	 * Add a marker to Map
	 * 
	 * @param latitude
	 *            : Latitude
	 * @param longitude
	 *            : Longitude
	 * @param title
	 *            : The message to be shown
	 */
	public Marker addMarker(double latitude, double longitude, String title,
			String snippet, boolean playAnimation) {
		MarkerOptions markerOptions = new MarkerOptions()
				.position(new LatLng(latitude, longitude)).title(title)
				.snippet(snippet);
		if (moveCameraToLocation) {
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(latitude, longitude)).zoom(zoomLevel)
					.build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		}
		if (MarkerColor != NONE)
			getColor(markerOptions);
		if (playAnimation) {
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(latitude, longitude)).bearing(45)
					.tilt(90).zoom(googleMap.getCameraPosition().zoom).build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		}
		return googleMap.addMarker(markerOptions);
	}

	/**
	 * Create a marker
	 * 
	 * @param latitude
	 *            : Latitude
	 * @param longitude
	 *            : Longitude
	 * @param title
	 *            : The message to be shown
	 * @param snippet
	 *            : The snippet to be shown
	 * @return Marker : Returns a newly created Marker
	 */
	public Marker createMarker(double latitude, double longitude, String title,
			String snippet) {
		MarkerOptions markerOptions = new MarkerOptions()
				.position(new LatLng(latitude, longitude)).title(title)
				.snippet(snippet);

		if (MarkerColor != NONE)
			getColor(markerOptions);

		return googleMap.addMarker(markerOptions);
	}

	/**
	 * @param latitude
	 *            : Latitude
	 * @param longitude
	 *            : Longitude
	 */
	public void zoomToLocation(double latitude, double longitude) {
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(latitude, longitude)).zoom(zoomLevel)
				.build();
		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	/**
	 * Add a marker to the map
	 * 
	 * @param latitude
	 *            : Latitude
	 * @param longitude
	 *            : Longitude
	 * @param title
	 *            : The message to be shown
	 * @param snippet
	 *            : The snippet to be shown
	 * @param moveCameraToLocation
	 *            : Move the camera to the location
	 * @param playAnimation
	 *            : Play animated move to location
	 * @return Marker : Returns a newly created Marker
	 */
	public Marker addMarker(double latitude, double longitude, String title,
			String snippet, boolean moveCameraToLocation, boolean playAnimation) {
		this.moveCameraToLocation = moveCameraToLocation;
		return addMarker(latitude, longitude, title, snippet, playAnimation);
	}

	/**
	 * Add a marker to the map
	 * 
	 * @param latitude
	 *            : Latitude
	 * @param longitude
	 *            : Longitude
	 * @param title
	 *            : The message to be shown
	 * @param snippet
	 *            : The snippet to be shown
	 * @param moveCameraToLocation
	 *            : Move the camera to the location
	 * @param playAnimation
	 *            : Play animated move to location
	 * @param zoomLevel
	 *            : The level of zoom need to be applied
	 * @return Marker : Returns a newly created Marker
	 */
	public Marker addMarker(double latitude, double longitude, String title,
			String snippet, boolean moveCameraToLocation,
			boolean playAnimation, byte zoomLevel) {
		this.zoomLevel = zoomLevel;
		this.moveCameraToLocation = moveCameraToLocation;
		return addMarker(latitude, latitude, title, snippet, playAnimation);
	}

	/**
	 * Add a marker to the map
	 * 
	 * @param latitude
	 *            : Latitude
	 * @param longitude
	 *            : Longitude
	 * @param title
	 *            : The message to be shown
	 * @param snippet
	 *            : The snippet to be shown
	 * @param moveCameraToLocation
	 *            : Move the camera to the location
	 * @param playAnimation
	 *            : Play animated move to location
	 * @param zoomLevel
	 *            : The level of zoom need to be applied
	 * @param MarkerColors
	 *            : Set the colors from MarkerColors Class
	 * @return Marker : Returns a newly created Marker
	 */
	public Marker addMarker(double latitude, double longitude, String title,
			String snippet, boolean moveCameraToLocation,
			boolean playAnimation, byte zoomLevel, byte MarkerColors) {
		this.zoomLevel = zoomLevel;
		this.moveCameraToLocation = moveCameraToLocation;
		setMarkerColors(MarkerColors);
		return addMarker(latitude, latitude, title, snippet, playAnimation);
	}

	/**
	 * Set the colors from MarkerColors Class
	 * 
	 * @param MarkerColors
	 *            : MarkerColors.COLOR_VALUE
	 */
	public void setMarkerColors(byte MarkerColors) {
		this.MarkerColor = MarkerColors;
	}

	/**
	 * Set type of maps from NORMAL, HYBRID, SATELLITE or TERRAIN
	 * 
	 * @param MapType
	 *            : Check the MapType class
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
	 * 
	 * @param enabled
	 *            : Enable Current Location
	 */
	public void setCurrentLocation(boolean enabled) {
		googleMap.setMyLocationEnabled(enabled);
	}

	/**
	 * To enable the zoom controls (+/- buttons)
	 * 
	 * @param enabled
	 *            : Enable Zoom Controls
	 */
	public void setZoomControlsEnabled(boolean enabled) {
		googleMap.getUiSettings().setZoomControlsEnabled(enabled);
	}

	/**
	 * To enable Zoom Gestures
	 * 
	 * @param enabled
	 *            : Enable Zoom Gestures
	 */
	public void setZoomGesturesEnabled(boolean enabled) {
		googleMap.getUiSettings().setZoomGesturesEnabled(enabled);
	}

	/**
	 * To enable Compass
	 * 
	 * @param enabled
	 *            : Enable Compass
	 */
	public void setCompassEnabled(boolean enabled) {
		googleMap.getUiSettings().setCompassEnabled(enabled);
	}

	/**
	 * To enable Location Button
	 * 
	 * @param enabled
	 *            : Enable Location Button
	 */
	public void setMyLocationButtonEnabled(boolean enabled) {
		googleMap.getUiSettings().setMyLocationButtonEnabled(enabled);
	}

	/**
	 * To enable Rotate Gestures
	 * 
	 * @param enabled
	 *            : Enable Rotate Gestures
	 */
	public void setRotateGesturesEnabled(boolean enabled) {
		googleMap.getUiSettings().setRotateGesturesEnabled(enabled);
	}

	private void setCameraToLatLong(LatLng... args) {
		LatLngBounds.Builder b = new LatLngBounds.Builder();
		for (LatLng m : args) {
			b.include(m);
		}
		LatLngBounds bounds = b.build();

		CameraUpdate cu = CameraUpdateFactory
				.newLatLngBounds(bounds, 25, 25, 5);
		googleMap.animateCamera(cu);
	}

	/**
	 * To draw a route path from a start point to end point
	 * 
	 * @param activity
	 *            : To show progress dialog when retrieving the route
	 * @param LatLng1
	 *            : The LatLng of start position
	 * @param LatLng2
	 *            : The LatLng of end position
	 * @param moveCameraToPosition
	 *            : Move the camera accordingly to the path
	 */
	public void drawRoutePath(final Activity activity, final LatLng LatLng1,
			final LatLng LatLng2, final boolean moveCameraToPosition) {

		class DrawRoutePath extends AsyncTask<String, Void, Void> {
			private PolylineOptions mPolylineOptions;
			private ProgressDialog mProgressDialog;
			private MapDirection mMapDirection;
			private Document mDocument;

			@Override
			protected void onPreExecute() {
				if (activity != null)
					mProgressDialog = ProgressDialog.show(activity,
							"Fetching Direction", "Please Wait...");
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(String... arg0) {
				try {
					mMapDirection = new MapDirection();
					mDocument = mMapDirection.getXMLFromLatLong(LatLng1,
							LatLng2, MapDirection.DRIVING);
				} catch (Exception e) {
					Log.e(LOG_TAG, "DrawRoutePath " + e.getMessage());
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				Log.d(LOG_TAG, "mDocument " + mDocument);
				ArrayList<LatLng> mLatLongList = mMapDirection
						.getLatLongDirectionsList(mDocument);
				mPolylineOptions = new PolylineOptions().width(
						drawRoutePath_pathWidth).color(drawRoutePath_pathColor);

				for (int i = 0; i < mLatLongList.size(); i++) {
					mPolylineOptions.add(mLatLongList.get(i));
				}
				if (moveCameraToPosition)
					setCameraToLatLong(LatLng1, LatLng2);
				if (activity != null)
					mProgressDialog.cancel();
				googleMap.addPolyline(mPolylineOptions);
				super.onPostExecute(result);
			}

		}
		new DrawRoutePath().execute("");
	}

	/**
	 * Draw a circular path around a LatLng
	 * 
	 * @param LatLng
	 *            : The start LatLng
	 * @param radius
	 *            : The radius in meters to draw the circle around
	 * @param moveCameraToPosition
	 *            : Move the camera accordingly to the path
	 * @return Circle: The Circle created is returned
	 */
	public Circle drawCircularRegion(LatLng LatLng, int radius,
			boolean moveCameraToPosition) {
		CircleOptions circleOptions = new CircleOptions().center(LatLng)
				.radius(radius);

		Circle circle = googleMap.addCircle(circleOptions);
		if (moveCameraToPosition)
			setCameraToLatLong(LatLng,
					getNewLatLongFromDistance(LatLng, radius));
		return circle;
	}

	/**
	 * Draw a polygon to the LatLngs Provided
	 * 
	 * @param moveCameraToPosition
	 *            : Move the camera accordingly to the path
	 * @param LatLngs
	 *            : The array of LatLngs to draw the polygon
	 * @return Polygon: The Polygon created is returned
	 */
	public Polygon drawPolygonRegion(boolean moveCameraToPosition,
			LatLng... LatLngs) {
		PolygonOptions mPolygonOptions = new PolygonOptions();
		for (LatLng latlng : LatLngs)
			mPolygonOptions.add(latlng);
		mPolygonOptions.strokeColor(Polygon_strokeColor);
		mPolygonOptions.fillColor(Polygon_fillColor);
		if (moveCameraToPosition)
			zoomToFitLatLongs(LatLngs);
		return googleMap.addPolygon(mPolygonOptions);
	}

	/**
	 * To zoom the camera to the best possible position so the camera can view
	 * all the markers
	 * 
	 * @param markers
	 *            : The array of markers to zoom to
	 */
	public void zoomToFitMarkers(Marker... markers) {
		LatLngBounds.Builder b = new LatLngBounds.Builder();
		for (Marker m : markers) {
			b.include(m.getPosition());
		}
		LatLngBounds bounds = b.build();
		CameraUpdate cu = CameraUpdateFactory
				.newLatLngBounds(bounds, 25, 25, 5);
		googleMap.animateCamera(cu);
	}

	/**
	 * To zoom the camera to the best possible position so the camera can view
	 * all the LatLng's
	 * 
	 * @param latlng
	 *            : The array of LatLng's to zoom to
	 */
	public void zoomToFitLatLongs(LatLng... latlng) {
		LatLngBounds.Builder b = new LatLngBounds.Builder();
		for (LatLng l : latlng) {
			b.include(l);
		}
		LatLngBounds bounds = b.build();
		CameraUpdate cu = CameraUpdateFactory
				.newLatLngBounds(bounds, 25, 25, 5);
		googleMap.animateCamera(cu);
	}

	/**
	 * To get distance between tow LatLongs in kilometers
	 * 
	 * @param latLng1
	 *            : The start point LatLng
	 * @param latLng2
	 *            : The end point LatLng
	 * @return double : distance in meters
	 */
	public double getDistanceBetweenLatLongs_Kilometers(LatLng latLng1,
			LatLng latLng2) {
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
		// in Kilometers
		return (dist);
	}

	/**
	 * To get a new LatLng from an existing LatLng and distance
	 * 
	 * @param latLng
	 *            : The start point
	 * @param distance
	 *            : The distance in meters
	 * @return LatLng : The new LatLng deduced
	 */
	public LatLng getNewLatLongFromDistance(LatLng latLng, float distance) {
		distance = distance / 1000;// in meters
		double lat1 = Math.toRadians(latLng.latitude);
		double lon1 = Math.toRadians(latLng.longitude);

		double lat2 = Math
				.asin(Math.sin(lat1) * Math.cos(distance / 6378.1)
						+ Math.cos(lat1) * Math.sin(distance / 6378.1)
						* Math.cos(1.57));
		double lon2 = lon1
				+ Math.atan2(
						Math.sin(1.57) * Math.sin(distance / 6378.1)
								* Math.cos(lat1),
						Math.cos(distance / 6378.1) - Math.sin(lat1)
								* Math.sin(lat2));
		lat2 = Math.toDegrees(lat2);
		lon2 = Math.toDegrees(lon2);
		return new LatLng(lat2, lon2);
	}

	/**
	 * Get the list of LatLng's from start point to end point to draw a route or
	 * path
	 * 
	 * @param startPoint
	 *            : The LatLng of start point
	 * @param endpoint
	 *            : The LatLng of end point
	 * @param mLatLongLoadedListner
	 *            : Listner once the LatLng is loaded
	 */
	public void getLatLongList(final LatLng startPoint, final LatLng endpoint,
			final LatLongLoadedListner mLatLongLoadedListner) {

		class LatLongRetriever extends AsyncTask<String, Void, Void> {
			private MapDirection mMapDirection;
			private Document mDocument;

			@Override
			protected Void doInBackground(String... arg0) {
				try {
					mMapDirection = new MapDirection();
					mDocument = mMapDirection.getXMLFromLatLong(startPoint,
							endpoint, MapDirection.DRIVING);

				} catch (Exception e) {
					Log.e(LOG_TAG, "DrawRoutePath " + e.getMessage());
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mLatLongLoadedListner.LatLongs(mMapDirection
						.getLatLongDirectionsList(mDocument));
				super.onPostExecute(result);
			}
		}
		new LatLongRetriever().execute("");
	}

	/**
	 * To play an animation of the route path drawn throug LatLng's
	 * 
	 * @param markers : The markers to which lat long is provided
	 * @param secondsDelay: The time delay for animation from one point to anothert in milliseconds
	 * @param drawMarkers : If the markers are to be drawn or not
	 */
	public void PlayRouteAnimation(final Marker[] markers,
			final int secondsDelay, boolean drawMarkers) {
		if (markers == null)
			throw new RuntimeException("latlngs values are null");
		else
			Log.d(LOG_TAG, "latlngs " + markers.length);
		currentMarkerPosition = 0;
		PolylineOptions mPolylineOptions = new PolylineOptions().width(
				drawRoutePath_pathWidth).color(drawRoutePath_pathColor);

		for (int i = 0; i < markers.length; i++) {
			mPolylineOptions.add(markers[i].getPosition());
			if (drawMarkers)
				markers[i] = addMarker(markers[i].getPosition().latitude,
						markers[i].getPosition().longitude,
						markers[i].getTitle(), markers[i].getSnippet(), false);
		}

		googleMap.addPolyline(mPolylineOptions);

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(markers[0].getPosition()).bearing(45).tilt(90).zoom(15)
				.build();

		mCancelableCallback = new CancelableCallback() {

			@Override
			public void onCancel() {
			}

			@Override
			public void onFinish() {
				Log.d(LOG_TAG, "onFinish currentPt " + currentMarkerPosition);
				if (++currentMarkerPosition < markers.length) {

					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(markers[currentMarkerPosition]
									.getPosition())
							.tilt(90)
							.bearing(
									getNewBearing(
											markers[(currentMarkerPosition - 1)]
													.getPosition(),
											markers[(currentMarkerPosition)]
													.getPosition())).zoom(15)
							.build();

					markers[currentMarkerPosition].showInfoWindow();
					markers[currentMarkerPosition - 1].hideInfoWindow();
					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition), secondsDelay,
							mCancelableCallback);
					animateMarker(markers[currentMarkerPosition - 1],
							markers[currentMarkerPosition].getPosition(), true);
					// markers[currentMarkerPosition-1].setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

				}
			}
		};

		googleMap.animateCamera(
				CameraUpdateFactory.newCameraPosition(cameraPosition),
				mCancelableCallback);
	}

	private float getNewBearing(LatLng l1, LatLng l2) {

		Location loc1 = new Location("");
		loc1.setLatitude(l1.latitude);
		loc1.setLongitude(l1.longitude);

		Location loc2 = new Location("");
		loc2.setLatitude(l2.latitude);
		loc2.setLongitude(l2.longitude);

		return loc1.bearingTo(loc2);
	}

	
	private void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = googleMap.getProjection();
		Point startPoint = proj.toScreenLocation(marker.getPosition());
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 500;

		final LinearInterpolator interpolator = new LinearInterpolator();

		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);
				double lng = t * toPosition.longitude + (1 - t)
						* startLatLng.longitude;
				double lat = t * toPosition.latitude + (1 - t)
						* startLatLng.latitude;
				marker.setPosition(new LatLng(lat, lng));

				if (t < 1.0) {
					handler.postDelayed(this, 16);
				} else {
					if (hideMarker) {
						marker.setVisible(false);
					} else {
						marker.setVisible(true);
					}
				}
			}
		});

	}

}
