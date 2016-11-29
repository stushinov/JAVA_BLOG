package softuni.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import softuni.blog.bindingModel.UserBindingModel;
import softuni.blog.entity.Role;
import softuni.blog.entity.User;
import softuni.blog.repository.RoleRepository;
import softuni.blog.repository.UserRepository;

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
