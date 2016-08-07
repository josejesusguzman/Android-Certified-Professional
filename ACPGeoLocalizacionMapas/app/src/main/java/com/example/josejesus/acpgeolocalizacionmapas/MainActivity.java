package com.example.josejesus.acpgeolocalizacionmapas;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PReguntar si los servicios de google se encuentran activos
        int codigoGooglePlay = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (codigoGooglePlay != ConnectionResult.SUCCESS) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(codigoGooglePlay, this, 6);
            if (dialog != null) {
                dialog.show();
            } else {
                Toast.makeText(MainActivity.this, "Error al verificar Google Play", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        //Mostrar el mapa en la aplicación adecuadamente
        SupportMapFragment supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map_fragment);
        googleMap = supportMapFragment.getMap();

        if (googleMap != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);

        }

        //Actualizar la unicacion mientras se mueve el dispositivo
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//proveedor
        if (location != null) {
            showLocation(location.getLatitude(), location.getLongitude());
        }

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                CameraUpdate camaraCentro = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                googleMap.moveCamera(camaraCentro);
                showLocation(location.getLatitude(), location.getLongitude());

            }
        };

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//PRecicion de la ubicacion
        criteria.setAltitudeRequired(true);
        criteria.setCostAllowed(false);//Si se pueden usar los datos
        criteria.setSpeedRequired(true);
        //Para buscar al mejor proveedor
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 1000, 70, (android.location.LocationListener) locationListener);//Provider, rango en smilisegundos. rango en metros, locationListener
        }

    }

    private void showLocation(double latitud, double longitud) {
        googleMap.addMarker(new MarkerOptions()
            .position(new LatLng(latitud, longitud))
            .title("Estoy aqui :3"));
    }

}
