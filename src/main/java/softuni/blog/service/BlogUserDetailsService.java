package softuni.blog.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.blog.entity.User;
import softuni.blog.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Admin on 23.11.2016 г..
 *
 * The next thing we need to implements is the so called "userService".
 * It is used to get user from the database and transform it to Spring Security User.
 */

//In order to tell Spring that this will be a service, we need to use the following annotation
//This will give our service a name
@Service("blogUserDetailsService")
public class BlogUserDetailsService implements UserDetailsService {

    //This private field has the "final" keyword, which means that we will not be able to change it after initialization
    private final UserRepository userRepository;

    //Here the constructor initializes the UserRepository
    public BlogUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
    * The idea behind this method is to get our user and make it object of type UserDetails.
    * This will give us the ability to login and do other things with our users.
    * We need to get a user by a given email. If the user does not exist, we will throw exception
    */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Invalid user");
        }
        else {

            Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());

            return new org
                    .springframework
                    .security
                    .core
                    .userdetails
                    .User(user.getEmail(), user.getPassword(), grantedAuthorities);
        }

        /*
        *Here we get all of the user roles and create a collection of authorities.
        * Then we create a new Spring Security User with the given email, password and authorities.
        * This is everything for our service, but we are not done, yet.
        */
    }

}
