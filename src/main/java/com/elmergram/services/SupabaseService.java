package com.elmergram.services;

import com.elmergram.utils.ImageUtils;
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

        ImageUtils.validateImage(fileBytes, originalFilename);

        String extension = originalFilename
                .substring(originalFilename.lastIndexOf('.') + 1)
                .toLowerCase();

        String contentType = switch (extension) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };

        String fileName = "posts/" + userId + "/" + UUID.randomUUID() + "." + extension;
        String url = supabaseUrl + "/storage/v1/object/elmergram/" + fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(supabaseKey);
        headers.set("apikey", supabaseKey);
        headers.set("x-upsert", "true");
        headers.setContentType(MediaType.parseMediaType(contentType));

        HttpEntity<byte[]> request = new HttpEntity<>(fileBytes, headers);

        restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

        return fileName;
    }


    // gets the image from supabase storage
    public String getPublicImageUrl(String filePath) {
        return supabaseUrl
                + "/storage/v1/object/public/elmergram/"
                + filePath;
    }



}
