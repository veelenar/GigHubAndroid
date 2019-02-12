package com.example.gighub;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db;
    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 0;
    public static String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView;
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new SQLiteDatabaseHandler(this);
        List<Gig> gigList = db.allGigs();
        Collections.reverse(gigList);


        //setting adapter to recyclerview

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_EXAMPLE_SWITCH, false);
        //creating recyclerview adapter
        try {
            if (switchPref == true) {
                getLocation();
                getCity();

                List<Gig> gigsFiltered = new LinkedList<Gig>();
                gigsFiltered.clear();
                for (Gig g : gigList) {
                    String venue = g.getVenue();
                    if (venue.contains(cityName)) {
                        gigsFiltered.add(g);
                    }
                }
                if(!gigsFiltered.isEmpty()) {
                    GigAdapter adapter = new GigAdapter(this, gigsFiltered);
                    recyclerView.setAdapter(adapter);
                }else{
                    GigAdapter adapter = new GigAdapter(this, gigList);
                    recyclerView.setAdapter(adapter);
                }
            } else {
                GigAdapter adapter = new GigAdapter(this, gigList);
                recyclerView.setAdapter(adapter);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.action_refresh:
                Intent refresh = new Intent(this, MainActivity.class);
                overridePendingTransition(0, 0);
                refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(refresh);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickAddGig(View view) {
        Intent intentAddGig = new Intent(getApplicationContext(), AddGigActivity.class);
        startActivity(intentAddGig);
    }


    public void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.i("GigHub", "Pytamy o uprawnienia");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);

        } else {
            Log.i("GigHub", "Są uprawnienia");

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    double alt = location.getAltitude();

                    Locale locale = Locale.getDefault();

                    String locInfo = String.format(locale, "Aktualne położenie: %f, %f, wysokość: %f", lat, lon, alt);
                    Log.i("GigHub", locInfo);

                    Geocoder gcd = new Geocoder(getApplicationContext(), locale);
                    List<Address> address = null;

                    try {
                        address = gcd.getFromLocation(lat, lon, 1);
                        String countryName = address.get(0).getCountryName();
                        String addressLine1 = address.get(0).getAddressLine(0);
                        String addressLine2 = address.get(0).getAddressLine(1);
                        String addressInfo;
                        if (addressLine2 == null) {
                            addressInfo = String.format(locale, "Adres: %s, %s", addressLine1, countryName);
                        } else {
                            addressInfo = String.format(locale, "Adres: %s, %s, %s", addressLine1, addressLine2, countryName);
                        }
                        Log.i("GigHub", addressInfo);
                        Log.i("GigHub", address.get(0).getLocality());

                        // PG: lat 54.3714246, lon 18.6191359
                        cityName = address.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });


        }
    }

    public String getCity() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return "Unknown";
            }
            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location!=null){
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                Locale locale = Locale.getDefault();
                Geocoder gcd = new Geocoder(getApplicationContext(), locale);
                List<Address> address = null;

                try {
                    address = gcd.getFromLocation(lat, lon, 1);
                    // PG: lat 54.3714246, lon 18.6191359
                    cityName = address.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return cityName;
    }
}
