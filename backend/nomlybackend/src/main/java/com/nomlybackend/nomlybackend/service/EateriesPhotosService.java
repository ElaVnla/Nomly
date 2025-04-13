package com.nomlybackend.nomlybackend.service;

import com.google.gson.Gson;
import com.nomlybackend.nomlybackend.model.eateries.PhotoDTO;
import com.nomlybackend.nomlybackend.repository.EateriesPhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class EateriesPhotosService {
    @Autowired
    EateriesPhotosRepository eateriesPhotosRepository;

    //TODO store API key in another file
    static String apiKey = "AIzaSyCCpR2DMrJpe4Lv3maS070IRysQWVevESs";

    public List<byte[]> getImages(String eateryId) throws URISyntaxException, IOException, InterruptedException {
        int heightPx = 213;
        int widthPx = 319;
        List<String> photos = eateriesPhotosRepository.findPhotoNamesByEateryId(eateryId);
        Gson gson = new Gson();
        List<byte[]> encodedImages = new ArrayList<>();
        for (String photoName: photos){ //todo multithread for each image
            String baseURI = "https://places.googleapis.com/v1/places/%s/photos/%s/media?key=%s&maxHeightPx=%d&maxWidthPx=%d&skipHttpRedirect=false";

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(String.format(baseURI, eateryId, photoName, apiKey, heightPx,widthPx)))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NEVER)
                    .build();

            HttpResponse<byte[]> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofByteArray());
            if (getResponse.statusCode() == 302) {
                String redirectUrl = getResponse.headers().firstValue("location").orElseThrow();
                byte[] encodedImage = ImageDownloadService.downloadImageAsBase64(redirectUrl);
                encodedImages.add(encodedImage);
            }
            else {
                throw new RuntimeException("Unexpected response: " + getResponse.statusCode());
            }
        }
        return encodedImages;
    }
}
