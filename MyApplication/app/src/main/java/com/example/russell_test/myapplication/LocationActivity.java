package com.example.russell_test.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    TextView relativeAddress;
    Button simLogin;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    Intent loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        txtLat = (TextView) findViewById(R.id.currentLocation);
        relativeAddress = (TextView) findViewById(R.id.relativeAddress);
        simLogin = (Button) findViewById(R.id.simulatedLogin);
        loginSuccess = new Intent(this, LobbyActivity.class);

        simLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginSuccess.putExtra("com.example.russell_test.myapplication.MESSAGE","Here is some cool text indicating that i logged in");

                startActivity(loginSuccess);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.currentLocation);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        Address relativeAddr = getAddress(location);

        relativeAddress.setText(relativeAddr.getAddressLine(0));


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    private Address getAddress(Location location)
    {
        Address address = null;
        double lat = location.getLatitude();
        double longt = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try
        {
            List<Address> listAddresses = geocoder.getFromLocation(lat, longt, 1);
            if(listAddresses.size() > 0)
            {
                address = listAddresses.get(0);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return address;
    }

    private Location getMidLocation(ArrayList<Location> locations)
    {
        Location midLocation = new Location("");

        double sumX = 0;
        double sumY = 0;

        int k = 0;
        int locCount = locations.size();

        for(k = 0; k < locCount; k++)
        {
            sumX += locations.get(k).getLatitude();
            sumY += locations.get(k).getLongitude();
        }

        midLocation.setLatitude( sumX / locCount);
        midLocation.setLongitude(sumY / locCount);

        return midLocation;
    }
}
