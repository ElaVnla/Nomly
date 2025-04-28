package com.nomlybackend.nomlybackend.service;

import com.google.gson.Gson;
import com.nomlybackend.nomlybackend.model.eateries.NearbyDTO;
import com.nomlybackend.nomlybackend.model.eateries.PlacesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GooglePlacesService {
    @Autowired
    GoogleApiProperties google;

    private final HttpClient httpClient;
    private final Gson gson;

    @Autowired
    public GooglePlacesService(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public PlacesDTO.Place[] getEateriesFromGoogle(NearbyDTO nearby) throws Exception{
        String[] fieldMask = {"places.id", "places.displayName.text", "places.priceLevel", "places.types", "places.rating", "places.photos.name","places.formattedAddress","places.location"};
        String jsonRequest = gson.toJson(nearby);

        HttpRequest postRequest = buildSearchNearbyRequest(jsonRequest, fieldMask);

        return sendRequest(postRequest);
    }

    private HttpRequest buildSearchNearbyRequest(String jsonRequest, String[] fieldMask) throws Exception{
        return HttpRequest.newBuilder()
                .uri(new URI("https://places.googleapis.com/v1/places:searchNearby"))
                .header("X-Goog-Api-Key", google.key())
                .header("X-Goog-FieldMask", String.join(",", fieldMask))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private PlacesDTO.Place[] sendRequest(HttpRequest postRequest) throws Exception{
        try {
            HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            PlacesDTO places = gson.fromJson(postResponse.body(), PlacesDTO.class);

            if (places == null || places.getPlaces() == null) {
                throw new IOException("No places found in the response.");
            }

            return places.getPlaces();
        } catch (IOException | InterruptedException e) {
            throw new Exception("Error fetching eateries from Google Places API", e);
        }
    }
}
