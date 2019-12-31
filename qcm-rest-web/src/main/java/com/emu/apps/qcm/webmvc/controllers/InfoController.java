package com.emu.apps.qcm.webmvc.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Profile("webmvc")
public class InfoController {
    @GetMapping("/")
    public RedirectView getInfoPage() {
        return new RedirectView("/actuator/info");
    }
}
