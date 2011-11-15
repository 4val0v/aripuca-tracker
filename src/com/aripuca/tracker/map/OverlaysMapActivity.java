package com.aripuca.tracker.map;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aripuca.tracker.MyApp;
import com.aripuca.tracker.R;
import com.aripuca.tracker.app.Constants;
import com.aripuca.tracker.map.MyMapActivity.MapOverlay;
import com.aripuca.tracker.util.TrackPoint;
import com.aripuca.tracker.util.Utils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

/**
 * Map activity
 */
public class OverlaysMapActivity extends MapActivity {

	/**
	 * 
	 */
	private MyApp myApp;

	/**
	 * 
	 */
	private MapView mapView;

	/**
	 * Track span values
	 */
	private int minLat = (int) (180 * 1E6);
	private int maxLat = (int) (-180 * 1E6);
	private int minLng = (int) (180 * 1E6);
	private int maxLng = (int) (-180 * 1E6);

	/**
	 * Map overlay class
	 */
	class MyItemizedOverlay extends com.google.android.maps.ItemizedOverlay<OverlayItem> {

		private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();

		private Context mContext;

		public MyItemizedOverlay(Drawable defaultMarker, Context context) {
			super(boundCenterBottom(defaultMarker));
			mContext = context;
		}

		public MyItemizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
		}

		public void addOverlay(OverlayItem overlay) {
			overlayItems.add(overlay);
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return overlayItems.get(i);
		}

		@Override
		public int size() {
			return overlayItems.size();
		}

		@Override
		protected boolean onTap(int index) {
			OverlayItem item = overlayItems.get(index);
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle(item.getTitle());
			dialog.setMessage(item.getSnippet());
			dialog.show();
			return true;
		}
		
	}

	/**
	 * Called when the activity is first created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		myApp = ((MyApp) getApplicationContext());

		setContentView(R.layout.map_view);

		mapView = (MapView) findViewById(R.id.mapview);

		mapView.setStreetView(true);
		mapView.setSatellite(false);

		mapView.setBuiltInZoomControls(true);

		// ---Add a location marker---
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.map_pin);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		
		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this);
		
		GeoPoint point = new GeoPoint(19240000,-99120000);
		OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
		
		GeoPoint point2 = new GeoPoint(35410000, 139460000);
		OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");		
		
		itemizedoverlay.addOverlay(overlayitem);
		itemizedoverlay.addOverlay(overlayitem2);
		
		mapOverlays.add(itemizedoverlay);	
		
		mapView.invalidate();
		
	}

	/**
	 * 
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
