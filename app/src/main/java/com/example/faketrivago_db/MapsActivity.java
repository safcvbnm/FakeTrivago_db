package com.example.faketrivago_db;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String choose_name,choose_address,choose_phone,choose_discount;
    private int choose_num_room;
    private float choose_latitube,choose_longitube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        choose_name = this.getIntent().getExtras().getString("name");
        choose_address = this.getIntent().getExtras().getString("address");
        choose_phone = this.getIntent().getExtras().getString("phone");
        choose_num_room = this.getIntent().getExtras().getInt("room_num");
        choose_latitube = this.getIntent().getExtras().getFloat("latitube");
        choose_longitube = this.getIntent().getExtras().getFloat("longitube");
        choose_discount = this.getIntent().getExtras().getString("discount");
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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String msg = "電話 : "+choose_phone+"\n"+"剩餘房間數 : "+choose_num_room+"\n"+"特價期間 : "+choose_discount;
                Toast.makeText(MapsActivity.this,msg, Toast.LENGTH_SHORT).show();
            }
        });
        LatLng position = new LatLng(choose_latitube, choose_longitube);
        moveMap(position);
        addMark(position,choose_name,choose_address);
    }

    private void moveMap(LatLng place){
        CameraPosition ps = new CameraPosition.Builder()
                .target(place)
                .zoom(20)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(ps));
    }

    private void addMark(LatLng place,String title,String snippet){
        MarkerOptions mark = new MarkerOptions();
        mark.position(place)
                .title(title)
                .snippet(snippet);
        mMap.addMarker(mark);
    }


}
