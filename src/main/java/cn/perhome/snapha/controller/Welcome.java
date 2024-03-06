package cn.perhome.snapha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class Welcome {

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String upload(){
        return "welcome";
    }

}
