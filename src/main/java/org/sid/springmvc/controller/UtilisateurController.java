package org.sid.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilisateurController {

    @GetMapping(path = "/users")
    public String utilisateurs(Model model){
        return "utilisateurs";
    }
}
