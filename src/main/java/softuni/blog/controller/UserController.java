package softuni.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import softuni.blog.bindingModel.UserBindingModel;
import softuni.blog.entity.Role;
import softuni.blog.entity.User;
import softuni.blog.repository.RoleRepository;
import softuni.blog.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Admin on 24.11.2016 г..
 *
 * Creating User Controller
 * This class will register new users, login the old ones, show us the profile page, etc.
 */

//This way we are telling Spring that this class can define routes and that will take care of actions related with our entities.
@Controller
public class UserController {




    //We are using the "@Autowired" annotation again, to tell Spring to initialize those fields.
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;





    //First of all, we need to check if there is a logged in user. We don't want guests to our blog to access the user's page.
    //Everyone else will be redirected to the login page.
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){

        //This will give us only the basic properties of our user.
        // That means only username (email in our case), roles and password.
        // We can use it to extract the current user from the database:
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                                                                    .getAuthentication()
                                                                    .getPrincipal();

        User user = this.userRepository.findByEmail(principal.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("view", "user/profile");

        return "base-layout";
    }






    // First of all, we are using the "@RequestMapping" annotation. This annotation combines "GET" and "POST"
    // requests (not only) and we need to specify that we are interested in the "GET" requests only.
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){


        //It checks if there is logged in user and if there is,
        // it simply tells the authentication module to logout the user. Then it redirects to the login page again.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }




    //To create a login, we need only 2 things. A method and a view. Spring Security will take care of the rest
    @GetMapping("/login")
    public String loginProcess(Model model){
        model.addAttribute("view", "user/login");

        return "base-layout";
    }




    //This method will have the hard job to create a new user. Spring will automatically map the form data to our binding model.
    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel){

        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            //Put a pop up messgae here
            return "redirect:/register";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),

                //Here we use the password encoder to encode our password, because we don't want to keep it like a plain text.
                bCryptPasswordEncoder.encode(userBindingModel.getPassword())
        );

        Role userRole = this.roleRepository.findByName("ROLE_USER");

        user.addRole(userRole);

        this.userRepository.saveAndFlush(user);
        return "redirect:/login";
    }




    /*
    We are using the "@GetMapping" annotation. This annotation defines that the type of request we are going to process in our method is "GET".
    That means that if someone sends data (i.e. user data), this method won't be called.
    This method will only be called if someone tries to open the page that is hidden behind the route.
    The model parameter will be used to send data to our view.
    */
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("view", "user/register");
        return "base-layout";
        /*
        * Some of may say "But hey, you are returning a string, not a view" and yes you will be right.
        * Spring however, 	will take that string and search for our view.
        * The Model object works with key-value pairs just like a dictionary (Map in Java).
        * You can see that we are using the addAttribute() method to tell our view, that the variable "view" should be replaced by "user/register".
        */
    }
}
