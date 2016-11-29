package softuni.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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


    
}
