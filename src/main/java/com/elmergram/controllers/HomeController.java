package com.elmergram.controllers;

import com.elmergram.responses.ApiResponse;
import com.elmergram.services.SupabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/dev")
public class    HomeController {
    private final SupabaseService supabaseService;

    @GetMapping("/")
    public String redirectToSwagger(){
        return "redirect:/swagger-ui/index.html";
    }


//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file)
//            throws IOException {
//
//        String path = supabaseService.uploadFile(
//                file.getBytes(),
//                file.getOriginalFilename(),
//                1L // test user id
//        );
//
//        return ResponseEntity.ok(path);
//    }
    }
