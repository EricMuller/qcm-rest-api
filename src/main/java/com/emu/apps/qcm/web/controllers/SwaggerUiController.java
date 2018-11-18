package com.emu.apps.qcm.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerUiController {
    @RequestMapping("/api")
    public String index() {
        return "swagger-ui";
    }
}