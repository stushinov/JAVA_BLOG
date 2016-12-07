package softuni.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.blog.bindingModel.CategoryBindingModel;
import softuni.blog.entity.Article;
import softuni.blog.entity.Category;
import softuni.blog.repository.ArticleRepository;
import softuni.blog.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 6.12.2016 г..
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;


    //GET listing of categories in the admins pannel
    @GetMapping("/")
    public String list(Model model){

        List<Category> categories = this.categoryRepository.findAll();

        //Here we are getting our categories and sorting them by id, because if we don't they will be sorted alphabetically.
        categories = categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("view", "admin/category/list");
        return "base-layout";
    }


    //Just a get method for the create category
    //This method will only return our creation form.
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view", "admin/category/create");
        return "base-layout";
    }


    //The method that actually creates categories using our CategoryBindingModel
    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel){

        //First, we need to check if the field is empty and if it is, we should redirect the user
        if(StringUtils.isEmpty(categoryBindingModel.getName())){
            return "redirect:/admin/categories/create";
        }

        Category category = new Category(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);

        return "redirect:/admin/categories/";
    }


    //Category edit GET method
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        //Gets current category
        Category category = this.categoryRepository.findOne(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");

        return "base-layout";
    }


    //Category edit POST method #That actually changes things in the database
    @PostMapping("/edit/{id}")
    public String createProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findOne(id);

        category.setName(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);

        return "redirect:/admin/categories/";
    }


    //Category delete GET method
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories";
        }

        Category category = this.categoryRepository.findOne(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return  "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.findOne(id);

        //Deletes the articles corresponding to the current category
        for(Article article : category.getArticles()){
            this.articleRepository.delete(article);
        }

        //Finally  we delete the category
        this.categoryRepository.delete(category);

        return "redirect:/admin/categories/";
    }


}
