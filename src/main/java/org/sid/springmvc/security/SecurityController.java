package org.sid.springmvc.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {

    @GetMapping(path = "/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(path = "/logout")
        public String logout(HttpServletRequest request) throws ServletException {
            request.logout();
            return "redirect:/login";
        }


    @GetMapping(path = "/errorNotAuthorized")
    public String error(){
        return "403";
    }


}
