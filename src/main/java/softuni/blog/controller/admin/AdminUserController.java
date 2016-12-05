package softuni.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.blog.bindingModel.UserEditBindingModel;
import softuni.blog.entity.Role;
import softuni.blog.entity.User;
import softuni.blog.repository.ArticleRepository;
import softuni.blog.repository.RoleRepository;
import softuni.blog.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 5.12.2016 г..
 *
 * Controller for the admin panel
 */

//We want to do something additional to our controller – create a default route for all methods.
// Why? Because all of our methods will start with "/admin/users" and we don't want to write this every time.
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RoleRepository roleRepository;



    //This method will list all users in a view
    @GetMapping("/")
    public String listUsers(Model model){

        //Get all users in a list
        List<User> users = this.userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";
    }



    //GET edit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

        //Some baseCase
        if(!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }

        //We are getting all of the roles from the database and sending them to the view
        //Then we are sending the user we are going to edit to the view as well

        User user = this.userRepository.findOne(id);
        List<Role> roles = this.roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return "base-layout";
    }


    //POST edit
    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, UserEditBindingModel userEditBindingModel){

        //baseCase
        if(!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);

        //We don't want to change the user password if the admin left the password fields empty
        if(!StringUtils.isEmpty(userEditBindingModel.getPassword()) && !StringUtils.isEmpty(userEditBindingModel.getConfirmPassword())){

            if(userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())){
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bCryptPasswordEncoder.encode(userEditBindingModel.getPassword()));
            }

            // In the first if-statement we check if the password fields are not empty.
            // Then, in the second if-statement we check if the two passwords match.
            // If the match, we encrypt the password and set it as a new password for our user.
        }
        // end


        //Editing the full name and/or Email
        user.setFullName(userEditBindingModel.getFullName());
        user.setEmail(userEditBindingModel.getEmail());

        Set<Role> roles = new HashSet<>();

        for(Integer roleId : userEditBindingModel.getRoles()){
            roles.add(this.roleRepository.findOne(id));
        }

        user.setRoles(roles);

        this.userRepository.saveAndFlush(user);

        return "redirect:/admin/users/";

    }
}
