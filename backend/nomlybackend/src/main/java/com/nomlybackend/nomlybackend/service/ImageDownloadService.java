package com.nomlybackend.nomlybackend.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class ImageDownloadService {
    public static byte[] downloadImageAsBase64(String photoUri) throws IOException {
        // Create URL object from photoUri
        URL url = new URL(photoUri);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();

        // Get input stream from the connection
        InputStream inputStream = connection.getInputStream();

        // Convert the input stream to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        // Read the image data into the byte array
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        // Convert the byte array to Base64
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Close streams
        byteArrayOutputStream.close();
        inputStream.close();

        return imageBytes;
    }
}