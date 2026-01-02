package com.elmergram.controllers;

import com.elmergram.services.SupabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class    HomeController {
    private final SupabaseService supabaseService;

    @GetMapping("/")
    public String redirectToSwagger(){
        return "redirect:/swagger-ui/index.html";
    }
    }
