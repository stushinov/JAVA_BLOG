package softuni.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.blog.entity.Article;
import softuni.blog.repository.ArticleRepository;

import java.util.List;

/**
 * Created by Admin on 24.11.2016 г..
 */

@Controller
public class HomeController {

    //We get teh article repository so we can include it in the homepage
    @Autowired
    private ArticleRepository articleRepository;


    //The default rooting to our page aka. homepage
    @GetMapping("/")
    public String index(Model model){

        // A list that holds our articles
        List<Article> articles = this.articleRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("articles", articles);
        return "base-layout";
    }
}
