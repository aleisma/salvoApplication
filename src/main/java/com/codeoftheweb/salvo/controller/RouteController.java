package com.codeoftheweb.salvo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD

@Controller
=======
@Controller
@RequestMapping("/")
>>>>>>> b6931d2aced2940232547e654c0f08be93954772
public class RouteController {
    @RequestMapping(value = "/")
    public String redictectPage(){
        return "redirect:/web/games.html";
    }
}
<<<<<<< HEAD
=======






>>>>>>> b6931d2aced2940232547e654c0f08be93954772
