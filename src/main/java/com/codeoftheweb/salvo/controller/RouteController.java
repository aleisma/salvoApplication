package com.codeoftheweb.salvo.controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class RouteController {
    @RequestMapping(value = "/")
    public String redictectPage(){
        return "redirect:/web/games.html";
    }
}
