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
import java.util.concurrent.*;

@Service
public class EateriesPhotosService {
    @Autowired
    EateriesPhotosRepository eateriesPhotosRepository;
    @Autowired
    GoogleApiProperties google;

    public List<byte[]> getImages(String eateryId) throws Exception {
        int heightPx = 606;
        int widthPx = 375;
        List<String> photos = eateriesPhotosRepository.findPhotoNamesByEateryId(eateryId);
        if (photos.isEmpty()) return null;

        String baseURI = "https://places.googleapis.com/v1/places/%s/photos/%s/media?key=%s&maxHeightPx=%d&maxWidthPx=%d&skipHttpRedirect=false";
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();

        List<CompletableFuture<byte[]>> futures = new ArrayList<>();

        for (int i = 0; i < 2; i++) { //now only return 1 photo, for 4 -> Math.min(4, photos.size())
            final String photoName = photos.get(i);
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI(String.format(baseURI, eateryId, photoName, google.key(), heightPx, widthPx)))
                    .GET()
                    .build();

            CompletableFuture<byte[]> future = httpClient.sendAsync(getRequest, HttpResponse.BodyHandlers.ofByteArray())
                    .thenCompose(response -> {
                        if (response.statusCode() == 302) {
                            String redirectUrl = response.headers().firstValue("location").orElseThrow();
                            return CompletableFuture.supplyAsync(() -> {
                                try {
                                    return ImageDownloadService.downloadImageAsBase64(redirectUrl);
                                } catch (IOException e) {
                                    throw new CompletionException(e);
                                }
                            });
                        } else {
                            return CompletableFuture.failedFuture(
                                    new RuntimeException("Unexpected response: " + response.statusCode()));
                        }
                    });

            futures.add(future);
        }

        // Wait for all to complete
        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allDone.join(); // Wait for completion

        // Collect results
        List<byte[]> encodedImages = futures.stream()
                .map(CompletableFuture::join)
                .filter(result -> result != null)
                .toList();

        return encodedImages;
    }
}
