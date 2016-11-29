package softuni.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.blog.repository.ArticleRepository;
import softuni.blog.repository.UserRepository;

/**
 * Created by Admin on 29.11.2016 г..
 */

@Controller
public class ArticleController {

    /*
     In short, Spring creates object of each type that we have in our application
     each time we start our application.It keeps them in something called Spring IoC Container.
     Using the "@Autowired" annotation, we tell Spring that it should initialize and configure our repositories automatically.
    */
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;





    //The "@GetMapping" annotation tells Spring that this method cannot be called if the user wants to submit data.
    // It should be only used for viewing data, in our case showing the form.
    // The "@PreAuthorize" annotation uses Spring Security. That annotation receives a parameter,
    // which tell the authentication module who can access our method.
    // We want to limit the article creation to logged in users only and that’s why we are using the "isAuthenticated()" parameter.
    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model){

        //This code will add to our model a key-value pair.
        //The key will be the view we want to render and the value is the path to our view.
        // We want to load the "create" file from the "article" folder. Then we simply tell Spring to use our base layout.
        model.addAttribute("view", "article/create");
        return "base-layout";
    }

}
