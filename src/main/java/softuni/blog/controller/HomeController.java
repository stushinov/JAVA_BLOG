package softuni.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.blog.entity.Article;
import softuni.blog.entity.Category;
import softuni.blog.repository.ArticleRepository;
import softuni.blog.repository.CategoryRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 24.11.2016 г..
 */

@Controller
public class HomeController {

    //We get teh article repository so we can include it in the homepage
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/category/{id}")
    public String listArticles(Model model, @PathVariable Integer id){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/";
        }

        Category category = this.categoryRepository.findOne(id);
        Set<Article> articles = category.getArticles();

        model.addAttribute("articles", articles);
        model.addAttribute("category", category);

        model.addAttribute("view", "home/list-articles");
        return "base-layout";
    }

    //The default rooting to our page aka. homepage
    @GetMapping("/")
    public String index(Model model){

        // A list that holds our articles
        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("categories", categories);
        return "base-layout";
    }


    @RequestMapping("/error/403")
    public String accessDenied(Model model){

        model.addAttribute("view", "error/403");

        return "base-layout";
    }
}
