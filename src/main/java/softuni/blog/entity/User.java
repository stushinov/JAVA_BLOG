package softuni.blog.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

        //Since we are not assigning a default role when a user is created we need to do this
        this.roles = new HashSet<>();
    }

    //Since spring needs a second constructor(no idea why) we create a second empty constructor that will bring us some useful features(The logic)
    public User() {}

    //This is the information that we will keep in our database: Unique ID, email, Full name, Password

    private Integer id;

    private String email;

    private String fullName;

    private String password;

    //Let us define the 'User' - 'Role relation'

    private Set<Role> roles;

    //The new thing we are using here is 'EAGER' which basically means that we want to load the roles together with the user
    //The other annotation will create a joining table for our relation and name it users_roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    //Lets create some annotations. We want our id to be generated automatically

    //The '@Id' annotations tells 'Hibernate' that this field will be the primary key to our database
    @Id
    //This annotation generates the field automatically
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    //The annotations of 'get' below are very similar

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * What we have done to the 'get' methods so far is:
     * 1. Defining the column name.
     * 2. Making them non-nullable(they can't take null values).
     * 3. For the password field, we limited the password length to 60 symbols.
     * 4. We told 'Hibernate' that the email is unique for every user.
    */

}
