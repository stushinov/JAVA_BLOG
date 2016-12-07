package softuni.blog.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuni.blog.bindingModel.ArticleBindingModel;
import softuni.blog.entity.Article;
import softuni.blog.entity.Category;
import softuni.blog.entity.Tag;
import softuni.blog.entity.User;
import softuni.blog.repository.ArticleRepository;
import softuni.blog.repository.CategoryRepository;
import softuni.blog.repository.TagRepository;
import softuni.blog.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 29.11.2016 г..
 *
 *
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;


    //This will be a helper method that will take a string and extract all tags from it
    private HashSet<Tag> findTagsFromString(String tagString){

        HashSet<Tag> tags = new HashSet<>();

        String[] tagNames = tagString.split(",\\s*");

        for(String tagName : tagNames){
            Tag currentTag = this.tagRepository.findByName(tagName);

            if(currentTag == null){
                currentTag = new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }

            tags.add(currentTag);
        }

        return tags;
    }

    /*
        The "@GetMapping" annotation tells Spring that this method cannot be called if the user wants to submit data.
       It should be only used for viewing data, in our case showing the form.
       The "@PreAuthorize" annotation uses Spring Security. That annotation receives a parameter,
       which tell the authentication module who can access our method.
       We want to limit the article creation to logged in users only and that’s why we are using the "isAuthenticated()" parameter.
    */
    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model){

        //This code will add to our model a key-value pair.
        //The key will be the view we want to render and the value is the path to our view.
        // We want to load the "create" file from the "article" folder. Then we simply tell Spring to use our base layout.

        //Gets categories so we can use it in the dropdown in the creation of an article
        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "article/create");
        return "base-layout";
    }




    /*
    * Before we talk about the "@PostMapping" annotation, take a look at the route.
    * It’s the exactly same route as the one we've used in our other method.
    * So, what have we done? With this annotation,
    * we told Spring that this method expects data that it needs to autofill in our binding model.
    * The annotation handles "POST" request that are usually what the HTML forms are using as a "method" of the request.
    * In summary, the other method will be called when the user wants to create new article (render the form)
     * and this method will be called when he submits the data.
    * */
    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel){

        //After all the problems with the @Column annotation and it's "nullable = false" i decided to this and it works
        if(articleBindingModel.getContent().equals("") || articleBindingModel.getTitle().equals("")){
            return "redirect:/article/create";
        }

        //get the currently logged in user:
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                                                              .getAuthentication()
                                                              .getPrincipal();
        //We are using the user repository to find a user by his email. Spring Security saves username, but in our case this is our email.
        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Category category = this.categoryRepository.findOne(articleBindingModel.getCategoryId());
        HashSet<Tag> tags = this.findTagsFromString(articleBindingModel.getTagString());

        //We upload it to our database, using our article repository
        Article articleEntity = new Article(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity,
                category,
                tags
        );

        this.articleRepository.saveAndFlush(articleEntity);

        return "redirect:/";

        /*
            Summary, we've got the user that Spring Security is using, then got the real entity user using his email.
        Then we've created a new article and saved it to the database. Finally, we've redirected the user to the home page.
        */
    }



    /*
    * Something new! In our route, we declare parameter using curly brackets.
    * Then in our method we use the "@PathVariable" annotation to tell Spring that this parameter should be taken from the URL.
    * We are now free to use it in our method.
    * The first thing we want to do is check if there is article with the given id in our database.
    * If such article doesn't exist, we will redirect the user to the home page:
    * */
    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id){
        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }


        //This will get our authentication token and check if the type of user is anonymous.
        //If the authentication is anonymous it means that we don't have logged in user.
        //In the body of our if, we need to get our current use
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        //Get article from database using our repository
        Article article = this.articleRepository.findOne(id);

        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");

        return "base-layout";
    }


    //Article edit-GET method
    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("view", "article/edit");
        model.addAttribute("article", article);
        model.addAttribute("categories", categories);

        return "base-layout";
    }


    //Article edit-POST method
    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, ArticleBindingModel articleBindingModel){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        //Get the current article
        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        Category category = this.categoryRepository.findOne(articleBindingModel.getCategoryId());

        article.setCategory(category);

        //Set the current article content to the input form in 'edit'
        article.setContent(articleBindingModel.getContent());

        //Set the current article title to the input form in 'edit'
        article.setTitle(articleBindingModel.getTitle());

        //Save the edited article to the database
        this.articleRepository.saveAndFlush(article);

        //redirects the user to the article details
        return "redirect:/article/" + article.getId();
    }


    //Article delete-GET method
    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete( Model model, @PathVariable  Integer id){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");

        return "base-layout";
    }


    //Article delete-POST method
    @PostMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id){

        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        this.articleRepository.delete(article);

        return "redirect:/";
    }

    //This method verifies if for the current article the user viewing is its user or admin
    private boolean isUserAuthorOrAdmin(Article article){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }
}
