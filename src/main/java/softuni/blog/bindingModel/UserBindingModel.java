package softuni.blog.bindingModel;

import javax.validation.constraints.NotNull;

/**
 * Created by Admin on 29.11.2016 г..
 *
 * This class will take the data from our register form and we will use it to create a new user.
 * In order to do that the binding model should contain the exact fields that our form has.
 */
public class UserBindingModel {

    //We are saying that those field cannot be null or the user isn’t valid
    @NotNull
    private String email;

    @NotNull
    private String fullName;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
