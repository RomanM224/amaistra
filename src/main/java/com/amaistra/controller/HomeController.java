package com.amaistra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("amaistra", "amaistraaaa");
        return modelAndView;
    } 
    
    @GetMapping("/orders")
    public ModelAndView orders() {
        ModelAndView modelAndView = new ModelAndView("orders");
        return modelAndView;
    } 
    
    @GetMapping("/contacts")
    public ModelAndView contacts() {
        ModelAndView modelAndView = new ModelAndView("contacts");
        return modelAndView;
    }

}
