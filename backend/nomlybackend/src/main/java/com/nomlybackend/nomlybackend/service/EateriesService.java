package com.nomlybackend.nomlybackend.service;

import com.nomlybackend.nomlybackend.model.DietaryRestrictions;
import com.nomlybackend.nomlybackend.model.eateries.*;
import com.nomlybackend.nomlybackend.model.eateries.Nearby.ExcludedType;
import com.nomlybackend.nomlybackend.model.eateries.Nearby.IncludedType;
import com.nomlybackend.nomlybackend.model.eateries.Nearby.NearbyDTO;
import com.nomlybackend.nomlybackend.model.eateries.Places.PlacesDTO;
import com.nomlybackend.nomlybackend.model.eateries.Places.PriceLevel;
import com.nomlybackend.nomlybackend.repository.EateriesRepository;
import com.nomlybackend.nomlybackend.repository.EateriesPhotosRepository;
import com.nomlybackend.nomlybackend.repository.UsersSessionsEateriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nomlybackend.nomlybackend.model.EateriesPhotos;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EateriesService {
    @Autowired
    EateriesRepository eateryRepository;
    @Autowired
    SessionsEateriesService sessionsEateriesService;
    @Autowired
    UsersSessionsEateriesRepository usersSessionsEateriesRepository;
    @Autowired
    EateriesPhotosRepository eateriesPhotosRepository;
    @Autowired
    GooglePlacesService googlePlacesService;

    public DietaryRestrictions getDiet(Integer sessionId) {
        // Get the user preferences for the given sessionId
        Set<String> preferencesList = new HashSet<>(usersSessionsEateriesRepository.findPreferencesBySessionId(sessionId));

        // Create Sets to store unique inclusions and exclusions
        Set<IncludedType> inclusionsSet = new HashSet<>();  // Add default inclusions
        Set<ExcludedType> exclusionsSet = new HashSet<>();  // Add default exclusions

        // Loop through each user's preferences
        for (String preferences : preferencesList) {
            String[] splitPreferences = preferences.split(",");

            // For each preference, map to the appropriate inclusion or exclusion type
            for (String preference : splitPreferences) {
                // Check for inclusions
                IncludedType inclusion = IncludedType.fromPreference(preference);
                if (inclusion != null) {
                    inclusionsSet.add(inclusion);  // Add to inclusions if valid
                }

                // Check for exclusions
                ExcludedType exclusion = ExcludedType.fromPreference(preference);
                if (exclusion != null) {
                    exclusionsSet.add(exclusion);  // Add to exclusions if valid
                }
            }
        }

        // Convert the Sets to arrays
        IncludedType[] inclusionsArray = inclusionsSet.toArray(new IncludedType[0]);
        ExcludedType[] exclusionsArray = exclusionsSet.toArray(new ExcludedType[0]);

        // Return both inclusions and exclusions as a DietaryRestrictions object
        return new DietaryRestrictions(inclusionsArray, exclusionsArray);
    }

    public List<Eateries> findEateries(LocationDTO locationDTO) throws Exception{
        NearbyDTO nearby = createNearby(locationDTO);
        PlacesDTO.Place[] placesEntities = findEateriesNearby(nearby);

        return saveEateriesAndPhotos(locationDTO, placesEntities);
    }

    private NearbyDTO createNearby(LocationDTO locationDTO) {
        return new NearbyDTO.NearbyBuilder()
                .setLatLong(locationDTO.getLatitude(), locationDTO.getLongitude())
                .addDietaryRestriction(getDiet(locationDTO.getSessionId()))
                .setMaxResultCount(1)
                .build();
    }

    private PlacesDTO.Place[] findEateriesNearby(NearbyDTO nearby) throws Exception{
        PlacesDTO.Place[] placesEntities = new PlacesDTO.Place[]{};
        boolean found = false;

        while (!found && nearby.getRange() < 2000){
            placesEntities = googlePlacesService.getEateriesFromGoogle(nearby);

            if (placesEntities != null && placesEntities.length > 0){
                found = true;
            }
            else {
                nearby.increaseRange();
            }
        }

        if(!found){ throw new Exception("No eateries found within range");}

        return placesEntities;
    }

    private List<Eateries> saveEateriesAndPhotos(LocationDTO locationDTO, PlacesDTO.Place[] placesEntities) {
        List<Eateries> eateries = new ArrayList<>();

        for (PlacesDTO.Place place: placesEntities){
            Eateries eatery = place.toEntity();
            eateries.add(eatery);
            saveEatery(eatery);
            savePhotos(place, eatery);
            sessionsEateriesService.addEateryToSession(locationDTO.getSessionId(), eatery); //"error": "An unexpected error occurred: No value present" means no sessionId in db
        }
        return eateries;
    }

    private void saveEatery(Eateries eatery){
        eateryRepository.save(eatery);
    }

    private void savePhotos(PlacesDTO.Place place, Eateries eatery) {
        List<Photo> photos = place.getPhotos();
        if (photos != null){
            for (Photo photo: place.getPhotos()){
                EateriesPhotos eateriesPhotos = new EateriesPhotos(eatery, photo.getName());
                eateriesPhotosRepository.save(eateriesPhotos);
            }
        }
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
        String location = body.get("location");
        String operationHours = body.get("operationHours");

        Eateries eatery= new Eateries(eateryId,displayName,latitude,longitude,priceLevel,cuisine,rating,location,operationHours);
        return new EateriesDTO(eateryRepository.save(eatery));
    }
}
