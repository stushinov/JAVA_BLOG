package softuni.blog.entity;

import javax.persistence.*;

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
}
