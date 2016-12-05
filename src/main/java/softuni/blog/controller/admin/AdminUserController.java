package softuni.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.blog.entity.User;
import softuni.blog.repository.ArticleRepository;
import softuni.blog.repository.RoleRepository;
import softuni.blog.repository.UserRepository;

import java.util.List;

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
}
