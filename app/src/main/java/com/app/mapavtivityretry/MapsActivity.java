package com.app.mapavtivityretry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private GoogleMap mMap;
    private BroadcastReceiver broadcastReceiver;
    double longtitude,latitude;
    private GoogleMap getMap;
    public void setLat(double lat){

        latitude = lat;
        System.out.print(latitude);
    }

    public void setLng(double lng){

        longtitude = lng;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(TAG, "OnCreate Called.........................................................................");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Onresume Called.........................................................................");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Onstart Called.........................................................................");
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        Log.d(TAG, "OnCreateView Called.........................................................................");

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {


                    MapsActivity fragment = new MapsActivity();
                    fragment.setLat(intent.getDoubleExtra("latitude",0));
                    fragment.setLng(intent.getDoubleExtra("longitude",0));
                    //FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();
                    //tr.add(R.id.content_frame, fragment, null);


                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));




        return super.onCreateView(name, context, attrs);


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
        Log.d(TAG, "OnMapReady Called Called.........................................................................");
        LatLng yourLocation = new LatLng(latitude, longtitude);

        mMap.addMarker(new MarkerOptions().position(yourLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
       // Sets map position.

        // Add a marker in Sydney and move the camera
        System.out.print("Lat = "+latitude+"   long = "+longtitude  + "...............................................");
       /* LatLng yourLocation = new LatLng(this.latitude, this.longtitude);

        mMap.addMarker(new MarkerOptions().position(yourLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }
}
