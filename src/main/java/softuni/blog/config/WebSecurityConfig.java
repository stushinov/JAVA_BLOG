package softuni.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Admin on 24.11.2016 г..
 *
 * We've got to the point, where we need to configure our Security module.
 */

//Overall, the configuration annotation will tell Spring that this is a configuration class,
// and the rest of the annotations will set different settings for it.

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Overall, the configuration annotation will tell Spring that this is a configuration class,
    // and the rest of the annotations will set different settings for it.
    @Autowired
    private UserDetailsService userDetailsService;


    //Here we are setting the default userDetailsService to use our field and we are setting the passwordEncoder to a more secure one.
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //This method is going to define what permissions are needed to access our blog.
    @Override
    protected void configure(HttpSecurity http)throws Exception{

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .permitAll().and()
                .formLogin().loginPage("/login")
                .usernameParameter("email").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/error/403")
                .and()
                .csrf();

        /*
        This code tells the authentication module, that every page can be accessed by every user.
        Then it tells us that the login request should be expected at the "/login" route.
         The parameter for login will be "email" and the parameter for password will be "password".
        The logout will lead to "/login?logout" and if there is any error with the permissions,
         we should receive view that tells us that we don’t have access.
        */
    }
}
