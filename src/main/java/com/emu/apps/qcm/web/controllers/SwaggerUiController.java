package com.emu.apps.qcm.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerUiController {
    @GetMapping("/api")
    public String index() {
        return "swagger-ui";
    }
}
