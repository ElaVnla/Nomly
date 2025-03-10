package com.w3itexperts.ombe.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentTrackingOrderBinding;

public class TrackingOrder extends Fragment implements OnMapReadyCallback {
    FragmentTrackingOrderBinding b;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentTrackingOrderBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());




    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
      /*  mMap = googleMap;

        // Add a marker and move the camera
        LatLng defaultLocation = new LatLng(-34, 151);  // Example coordinates
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));*/

        mMap = googleMap;

        // Add start and end markers
        LatLng startPoint = new LatLng(25.152378, 75.850824); // Example start coordinates
        LatLng endPoint = new LatLng(25.157156, 75.853100);      // Example end coordinates

        mMap.addMarker(new MarkerOptions().position(startPoint).title("Start Point"));
        mMap.addMarker(new MarkerOptions().position(endPoint).title("End Point"));

        // Draw a route between the two points
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(startPoint)
                .add(new LatLng(25.154458, 75.852628)) // Example intermediate point
                .add(endPoint)
                .color(Color.GREEN)
                .width(10);

        mMap.addPolyline(polylineOptions);
        // Move the camera to show the route
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startPoint);
        builder.include(endPoint);
        LatLngBounds bounds = builder.build();
        int padding = 100; // Padding around the route
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

        // Add a custom info window for estimated time
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null; // Use default info window
            }

            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                // Inflate your custom info window layout
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                TextView title = infoWindow.findViewById(R.id.info_window_title);
                TextView snippet = infoWindow.findViewById(R.id.info_window_snippet);

                title.setText(marker.getTitle());
                snippet.setText("Estimated Time: 5-10 min"); // Custom text

                return infoWindow;
            }
        });

        // Show info window at a specific location
//        Marker midPointMarker = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(51.5045, -0.1337))
//                .title("Estimated Time")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//        if (midPointMarker != null) {
//            midPointMarker.showInfoWindow();
//        }



    }
}
