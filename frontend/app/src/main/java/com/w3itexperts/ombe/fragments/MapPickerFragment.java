package com.w3itexperts.ombe.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.w3itexperts.ombe.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapPickerFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private LatLng selectedLatLng;
    private String selectedAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        view.findViewById(R.id.confirmLocationBtn).setOnClickListener(v -> {
            if (selectedLatLng != null && selectedAddress != null) {

                Log.d("MAP_PICKED", "lat: " + selectedLatLng.latitude + ", lng: " + selectedLatLng.longitude);

                Bundle result = new Bundle();
                result.putDouble("lat", selectedLatLng.latitude);
                result.putDouble("lng", selectedLatLng.longitude);
                result.putString("locationName", selectedAddress);

                getParentFragmentManager().setFragmentResult("locationPicked", result);
                requireActivity().onBackPressed(); // Go back to PlanSessionFragment
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng singapore = new LatLng(1.3521, 103.8198);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11f));

        gMap.setOnMapClickListener(latLng -> {
            gMap.clear();
            gMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            selectedLatLng = latLng;

            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    selectedAddress = addresses.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                selectedAddress = "Unknown location";
            }
        });
    }
}