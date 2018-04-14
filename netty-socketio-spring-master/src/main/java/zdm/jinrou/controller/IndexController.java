package zdm.jinrou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import zdm.jinrou.util.MyMaps;

@RestController
@RequestMapping
public class IndexController {

    @RequestMapping(value = "/index.htm",method = RequestMethod.GET)
    public ModelAndView helloWorld(){
        return new ModelAndView("index.ftl", MyMaps.newHashMap("text","HelloWorld"));
    }


    @RequestMapping("/string")
    public String hello(){
        return "This is a String";
    }
}
