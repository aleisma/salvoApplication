package com.codeoftheweb.salvo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")

public class RouteController {
    @RequestMapping(value = "/")
    public String redictectPage(){
        return "redirect:/web/games.html";
    }
}
