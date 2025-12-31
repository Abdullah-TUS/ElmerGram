package com.elmergram.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class SupabaseService {

    @Value("${SUPABASE_URL}")

    private  String supabaseUrl;
    @Value("${SUPABASE_SERVICE_KEY}")
    private  String supabaseKey;
    private final RestTemplate restTemplate=new RestTemplate();

    // stores image in supabase Storage
    public String uploadFile(byte[] fileBytes, String originalFilename, int userId) {

        String extension = "bin";
        String contentType = "application/octet-stream";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            contentType = switch (extension) {
                case "png" -> "image/png";
                case "jpg", "jpeg" -> "image/jpeg";
                case "gif" -> "image/gif";
                default -> "application/octet-stream";
            };
        }

        String generatedFileName =
                "posts/" + userId + "/" + UUID.randomUUID() + "." + extension;

        String url = supabaseUrl + "/storage/v1/object/elmergram/" + generatedFileName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseKey);
        headers.set("apikey", supabaseKey);
        headers.set("x-upsert", "true");
        headers.setContentType(MediaType.parseMediaType(contentType));

        HttpEntity<byte[]> request = new HttpEntity<>(fileBytes, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Supabase upload failed");
        }

        return generatedFileName;
    }

    // gets the image from supabase storage
    public String getPublicImageUrl(String filePath) {
        return supabaseUrl
                + "/storage/v1/object/public/elmergram/"
                + filePath;
    }



}
