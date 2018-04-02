package ru.kosinov.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IndexController {

    @RequestMapping(value = "/index.htm",method = RequestMethod.GET)
    public String helloWorld(){
        return "HelloWorld";
    }

}
