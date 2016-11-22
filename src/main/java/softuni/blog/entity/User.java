package softuni.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Admin on 22.11.2016 г..
 *
 * User entity
 */

//The annotation below means that the class 'User' is going to be an Entity that will be saved to our database
@Entity
//Defines the table name in our database
@Table(name = "users")
public class User {


    //This constructor will help us with the user creation
    public User(String email, String fullName, String password){

        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    //Since spring needs a second constructor(no idea why) we create a second empty constructor that will bring us some useful features(The logic)
    public User() {}

    //This is the information that we will keep in our database: Unique ID, email, Full name, Password

    private Integer id;

    private String email;

    private String fullName;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
