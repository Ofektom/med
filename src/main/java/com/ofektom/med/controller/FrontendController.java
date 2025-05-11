package com.ofektom.med.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping(value = {"/", "/index", "/{path:[^\\.]*}"})
    public String redirect() {
        return "forward:/index.html";
    }
}
