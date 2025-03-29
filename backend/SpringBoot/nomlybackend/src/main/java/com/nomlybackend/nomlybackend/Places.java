package com.nomlybackend.nomlybackend;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Places {
    public static void main(String[] args) throws Exception{
        Nearby nearby = new Nearby();
        //TODO 1.1 set up location with IP for windows (testing)
        //TODO 1.2 set up location with GPS for android (production)
        nearby.setLatLong(1.334840, 103.964714);
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(nearby);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("https://places.googleapis.com/v1/places:searchNearby"))
                //TODO 2 set up api key in Constants.API_KEY
                .header("X-Goog-Api-Key", "AIzaSyCCpR2DMrJpe4Lv3maS070IRysQWVevESs")
                .header("X-Goog-FieldMask", "*")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(postResponse.body());
    }
}
