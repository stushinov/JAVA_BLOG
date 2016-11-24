package softuni.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Admin on 24.11.2016 г..
 */

@Controller
public class HomeController {

    //The default rooting to our page aka. homepage
    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("view", "home/index");
        return "base-layout";
    }
}
