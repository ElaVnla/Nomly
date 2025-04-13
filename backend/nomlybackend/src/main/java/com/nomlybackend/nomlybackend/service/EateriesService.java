package com.nomlybackend.nomlybackend.service;

import com.google.gson.Gson;
import com.nomlybackend.nomlybackend.model.eateries.*;
import com.nomlybackend.nomlybackend.repository.EateriesRepository;
import com.nomlybackend.nomlybackend.repository.EateriesPhotosRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nomlybackend.nomlybackend.model.EateriesPhotos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EateriesService {
    @Autowired
    EateriesRepository eateryRepository;
    @Autowired
    EateriesPhotosRepository eateriesPhotosRepository;

    //TODO store API key in another file
    static String apiKey = "AIzaSyCCpR2DMrJpe4Lv3maS070IRysQWVevESs";
//    @Value("${google.api.key}")
//    private String apiKey;

    //TODO Return Restaurant[]
    public List<Eateries> findEateries(LocationDTO locationDTO) throws Exception{
        Nearby nearby = new Nearby(locationDTO.getLatitude(), locationDTO.getLongitude());

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(nearby);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("https://places.googleapis.com/v1/places:searchNearby"))
                //TODO 2 set up api key in Constants.API_KEY
                .header("X-Goog-Api-Key", apiKey)
                .header("X-Goog-FieldMask", "places.id,places.displayName.text,places,places.priceLevel,places.types,places.rating,places.photos.name")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        PlacesDTO places = gson.fromJson(postResponse.body(), PlacesDTO.class);

        List<Eateries> eateries = new ArrayList<>();
        for (PlacesDTO.Place place: places.getPlaces()){ //local9 error response means invalid latlong
            Eateries eatery = place.toEntity();
            eateries.add(eatery);
            eateryRepository.save(eatery);
            for (Photo photo: place.getPhotos()){
                EateriesPhotos eateriesPhotos = new EateriesPhotos(eatery, photo.getName());
                eateriesPhotosRepository.save(eateriesPhotos);
            }
        }
        return eateries;
    }



    public List<EateriesDTO> getAllEateries(){
        List<Eateries> eateries = eateryRepository.findAll();

        return eateries.stream().map(eatery -> new EateriesDTO(eatery)).collect(Collectors.toList());
    }

    public EateriesDTO getEateryById(String id){
        Eateries eatery = eateryRepository.findById(id).get();
        return new EateriesDTO(eatery);
    }

    public Eateries getEateryEntityById(String id) {
        return eateryRepository.findById(id).get();
    }

    public boolean deleteEaterybyId(String id){
        if(!eateryRepository.findById(id).equals(Optional.empty())){
            eateryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EateriesDTO updateEateryById(String id,Map<String,String> body){
        Eateries current = eateryRepository.findById(id).get();

        current.setEateryId(body.get("eateryId"));
        current.setDisplayName(body.get("displayName"));
        current.setLatitude(Double.parseDouble(body.get("latitude")));
        current.setLongitude(Double.parseDouble(body.get("longitude")));
        current.setPriceLevel(PriceLevel.valueOf(body.get("priceLevel")));
        current.setCuisine(body.get("cuisine"));
        current.setRating(Double.parseDouble(body.get("rating")));
        current.setOperationHours(body.get("operationHours"));


        eateryRepository.save(current);
        return new EateriesDTO(current);

    }


    public EateriesDTO createEatery(Map<String,String> body){

        String eateryId = body.get("eateryId");
        String displayName = body.get("displayName");
        Double latitude = Double.parseDouble(body.get("latitude"));
        Double longitude = Double.parseDouble(body.get("longitude"));
        PriceLevel priceLevel = PriceLevel.valueOf(body.get("priceLevel"));
        String cuisine = body.get("cuisine");
        Double rating = Double.parseDouble(body.get("rating"));
        String operationHours = body.get("operationHours");

        Eateries eatery= new Eateries(eateryId,displayName,latitude,longitude,priceLevel,cuisine,rating,operationHours);
        return new EateriesDTO(eateryRepository.save(eatery));
    }
}
