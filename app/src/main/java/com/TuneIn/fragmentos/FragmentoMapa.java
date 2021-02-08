package com.TuneIn.fragmentos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.TuneIn.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class FragmentoMapa extends Fragment {

    View view;
    GoogleMap googleMap;
    Location ubicacionActual;
    FusedLocationProviderClient client;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inicializar location client
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        // Inicializar la vista
        view = inflater.inflate(R.layout.mapa_fragment, container, false);

        // Inicializar el mapaFragmento
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        // Async Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                // Cuando el mapa este listo
                googleMap = gMap;

                configurarPermisos();
            }
        });
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String
            permissions[], int[] grantResults) {
        switch (requestCode) {
            case 9999: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    configurarPermisos();
                }
            }
        }
    }

    public void configurarPermisos() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }

        // Luego de obtener el permiso:
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);


        // Obtener y mostrar la posición actual
        obtenerPosicion();


    }

    @SuppressLint("MissingPermission") // Porque ya antes se han solicitado los permisos
    public void obtenerPosicion() {
        //TODO https://www.youtube.com/watch?v=VdCQoJtNXAg

        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        ubicacionActual = location;
                        Log.d("MAPA", "bien" + ubicacionActual.getLongitude() + " and " + ubicacionActual.getAltitude());
                    } else {
                        Log.d("MAPA", "ERROR, ubicacion vacia");
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                ubicacionActual = location1;
                            }
                        };
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                    LatLng latLng = new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                    googleMap.animateCamera(CameraUpdateFactory.zoomIn());

                }
            });
        }
    }

        //TODO https://www.youtube.com/watch?v=p0PoKEPI65o == con esta forma retorna ubicacion vacia
       /* @SuppressLint("MissingPermission")
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>(){
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d("MAPA", "ERROR, ubicacion vacia");
                        }else{
                            ubicacionActual = location;
                            LatLng latLng = new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng)
                                    .title("Estas aquí");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                            googleMap.addMarker(options);
                        }
                    };
                });
    }*/
    public static FragmentoMapa newInstance(String text) {

        FragmentoMapa f = new FragmentoMapa();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }



}