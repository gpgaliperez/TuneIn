package com.TuneIn.fragmentos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.TuneIn.Entidades.Concierto;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static androidx.core.content.ContextCompat.getSystemService;

public class MapaFragment extends Fragment {

    View view;
    MapView mapView;
    GoogleMap googleMap;
    Location ubicacionActual;
    FusedLocationProviderClient mFusedLocationClient;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inicializar location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());


        // Inicializar la vista
       view = inflater.inflate(R.layout.mapa_fragment, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        /*mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // Async Map
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                // Cuando el mapa este listo
                googleMap = gMap;

                configurarPermisos();
            }
        });

        // Inicializar el mapaFragmento
        //SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        // Async Map
        /*supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                // Cuando el mapa este listo
                googleMap = gMap;

                configurarPermisos();
            }
        });*/
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
        obtenerUltimaPosicion();
        // TODO https://www.geeksforgeeks.org/how-to-get-user-location-in-android/

        // Agregar los conciertos
        markerConciertos();
    }

    @SuppressLint("MissingPermission")
    public void obtenerUltimaPosicion(){

        if (isLocationEnabled()) {

            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {

                        // Agrega el marcador en la ubicacion actual
                        LatLng latLng = new LatLng( location.getLatitude(), location.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(latLng)
                                .title("Estas aquí");
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

                        googleMap.addMarker(options);
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "Por favor active su ubicación", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            LatLng latLng = new LatLng( mLastLocation .getLatitude(), mLastLocation .getLongitude());

            MarkerOptions options = new MarkerOptions().position(latLng)
                    .title("Estas aquí");
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
            googleMap.addMarker(options);
        }
    };


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("PotentialBehaviorOverride")
    public void markerConciertos(){
        List<Concierto> listaConciertos =ConciertoFragment.conciertosList;

        for(Concierto concierto : listaConciertos ){
            // Color random para cada artista
            BitmapDescriptor color = BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360));

            LatLng latLng = new LatLng( concierto.getVenue().getLocation().getLat(), concierto.getVenue().getLocation().getLon());
            MarkerOptions options = new MarkerOptions().position(latLng)
                    .icon(color)
                    .title(concierto.getTitle())
                    .snippet(concierto.getVenue().getCity() + ", " + concierto.getVenue().getCountry() );

            googleMap.addMarker(options).setTag(concierto);

        }

       googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @SuppressLint("PotentialBehaviorOverride")
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Concierto c = (Concierto) marker.getTag();
                intent.setData(Uri.parse(c.getUrl()));
                startActivity(intent);
            }
        });
    }


    //////
    public static MapaFragment newInstance(String text) {

        MapaFragment f = new MapaFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}