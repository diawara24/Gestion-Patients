package org.sid.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MedecinController {

    @GetMapping(path = "/medecins")
    public String medecins(Model model){
        return "medecins";
    }
}
