package softuni.blog.entity;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 22.11.2016 г..
 * Role entity
 */

//The annotation below means that the class 'User' is going to be an Entity that will be saved to our database
@Entity
//Defines the table name in our database
@Table(name = "roles")
public class Role {

    private Integer id;
    private String name;

    //Let us create our relation between 'User' - 'Role'.
    //Our relation will be of type 'Many-to-many' which means that many users can have many roles.
    //In order to do that relation we need to create a collection of users
    //This field will contain only unique users and it will tells us which user has what role

    public Role(){
        this.users = new HashSet<>();
    }

    //To use this set we must initialize it in our a constructor
    private Set<User> users;

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    //We told it to auto generate an Identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName(){
        return StringUtils.capitalize(this.getName().substring(5).toLowerCase());
    }
}
