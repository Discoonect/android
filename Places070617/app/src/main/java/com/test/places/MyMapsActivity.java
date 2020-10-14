package com.test.places;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.places.model.Store;

import java.util.ArrayList;

public class MyMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double lat;
    double lng;
    ArrayList<Store> storeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // 내 폰의 현재 위치값을 받아온다.
        Intent i = getIntent();
        lat = i.getDoubleExtra("lat", 37.5412538);
        lng = i.getDoubleExtra("lng", 126.8359702);
        storeArrayList = (ArrayList<Store>) i.getSerializableExtra("storeList");

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng me = new LatLng(lat, lng);

        for(Store store : storeArrayList){
            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(store.getLat(), store.getLng()))
                    .title(store.getName()).snippet(store.getAddr());
            mMap.addMarker(options);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 16));
    }
}