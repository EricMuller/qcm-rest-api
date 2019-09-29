package com.emu.apps.qcm.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class InfoController {
    @GetMapping("/")
    public RedirectView getInfoPage() {
        return new RedirectView("/actuator/info");
    }
}
