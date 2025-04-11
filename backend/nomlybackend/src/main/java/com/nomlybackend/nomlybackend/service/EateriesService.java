package com.nomlybackend.nomlybackend.service;

import com.google.gson.Gson;
import com.nomlybackend.nomlybackend.model.eateries.Eateries;
import com.nomlybackend.nomlybackend.model.eateries.LocationDTO;
import com.nomlybackend.nomlybackend.model.eateries.Nearby;
import com.nomlybackend.nomlybackend.model.eateries.PlacesDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class EateriesService {
    //TODO store API key in another file
//    @Value("${google.api.key}")
//    private String apiKey;

    //TODO Return Restaurant[]
    public static List<Eateries> findEateries(LocationDTO locationDTO) throws Exception{
        Nearby nearby = new Nearby(locationDTO.getLatitude(), locationDTO.getLongitude());

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(nearby);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("https://places.googleapis.com/v1/places:searchNearby"))
                //TODO 2 set up api key in Constants.API_KEY
                .header("X-Goog-Api-Key", "AIzaSyCCpR2DMrJpe4Lv3maS070IRysQWVevESs")
                .header("X-Goog-FieldMask", "places.id,places.displayName.text,places,places.priceLevel,places.types,places.rating") //,places.photos.googleMapsUri
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        PlacesDTO places = gson.fromJson(postResponse.body(), PlacesDTO.class);

        List<Eateries> eateries = new ArrayList<>();
        for (PlacesDTO.Place place: places.getPlaces()){ //local9 means invalid latlong
            Eateries eatery = place.toEntity();
            eateries.add(eatery);
            //TODO 1: Add Eatery to database after sorting out the schema
            //eateryRepository.save(eatery);
        }
        return eateries;
    }
}
