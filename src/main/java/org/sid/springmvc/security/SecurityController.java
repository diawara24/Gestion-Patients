package org.sid.springmvc.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(path = "/errorNotAuthorized")
    public String error(){
        return "403";
    }

    @GetMapping(path = "/connect")
    public String login(){
        return "login";
    }
}
