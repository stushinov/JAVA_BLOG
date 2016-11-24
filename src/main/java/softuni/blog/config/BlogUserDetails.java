package softuni.blog.config;

import org.springframework.util.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import softuni.blog.entity.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Admin on 23.11.2016 г..
 *
 * In order to use the built-in functionality from Spring Security
 * we need to create a new class, that will make sure that we are creating the users using the right way.
 */
public class BlogUserDetails extends User implements UserDetails {

    @Override
    public boolean isAccountNonExpired(){return true;}

    @Override
    public boolean isAccountNonLocked(){return true;}

    @Override
    public boolean isCredentialsNonExpired(){return true;}

    @Override
    public boolean isEnabled(){return true;}

    /*We are forced to override some of the methods in the "UserDetails" interface.
    That is not all of them, but before we continue,
     let us create two new private fields that will keep our current user and his roles:*/

    //The User is our entity type User and the roles is a simple list collection
    private ArrayList<String> roles;
    private User user;

    //Lets create the constructor to intialize user and roles
    public BlogUserDetails(User user, ArrayList<String> roles){

        /*As you can see we're setting our roles and user fields using the parameters we are taking in the constructor.
        * the method 'super()' is complicated so it will be discussed in later courses in SoftUni.
        * */
        super(user.getEmail(), user.getFullName(), user.getPassword());

        this.roles = roles;
        this.user = user;
    }


    /*
    This will get our roles (that we currently keep as strings) and join them into one string.
    Then it will return collection of authorities. The authorities in Spring are the things we call "roles" or "permissions".
    With that our class is almost ready. The only thing left is to create a method that will return our current user:
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        String userRoles = StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    public User getUser(){
        return this.user;
    }
}
