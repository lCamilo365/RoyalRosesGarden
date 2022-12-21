package com.example.royalrosesgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class Maps extends AppCompatActivity {
    private MapView map;
    private MapController mapController;
    private Button btnProductBackMaps1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        btnProductBackMaps1 = (Button) findViewById(R.id.btnProductBackMaps1);

        btnProductBackMaps1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Products.class);
                startActivity(intent);
            }
        });

        map = (MapView) findViewById(R.id.mapMaps);
        map.setTileSource(TileSourceFactory.MAPNIK);

        //map.setBuiltInZoomControls(true);
        GeoPoint bogota = new GeoPoint(4.60971, -74.08175);

        //https://www.geodatos.net/

        mapController = (MapController) map.getController();

        mapController.setCenter(bogota);
        mapController.setZoom(10);
        map.setMultiTouchControls(true);
        Intent intentIN = getIntent();
        ArrayList<String> latitudes = intentIN.getStringArrayListExtra("latitudes");
        ArrayList<String> longitudes = intentIN.getStringArrayListExtra("longitudes");


        for(int i=0; i<latitudes.size(); i++){
            GeoPoint geoPoint = new GeoPoint(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i)));
            Marker marker = new Marker(map);
            marker.setPosition(geoPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(marker);
        }

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Marker marker = new Marker(map);
                marker.setPosition(p);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(marker);
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, mapEventsReceiver);
        map.getOverlays().add(mapEventsOverlay);
/*
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        btnProductBackMaps = (Button) findViewById(R.id.btnProductBackMaps);
        setContentView(R.layout.maps);
*/
        /*
        setContentView(R.layout.maps);

        map = (MapView) findViewById(R.id.mapMaps);
        map.setTileSource(TileSourceFactory.MAPNIK);

        //map.setBuiltInZoomControls(true);
        mapController = (MapController) map.getController();

        GeoPoint bogota = new GeoPoint(4.60971, -74.08175);

        //https://www.geodatos.net/
        mapController.setCenter(bogota);
        mapController.setZoom(11);
        map.setMultiTouchControls(true);
*/
        /*
        btnProductBackMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

         */



    }




}