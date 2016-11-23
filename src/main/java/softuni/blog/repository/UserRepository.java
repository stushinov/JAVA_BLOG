package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.blog.entity.User;

/**
 * Created by Admin on 23.11.2016 г..
 *
 * Now, we are not exactly finding a way to get the users.
 * There is a way called "Repositories". You can imagine that the repository is our local access to the database.
 * Using methods in our repositories, we will get the entities from our database and use them locally.
 */



public interface UserRepository extends JpaRepository<User, Integer>{

//This will give us some methods that we are going to use later on in our blog,
// but for now we want to create the following method in our repository:

    User findByEmail(String email);

    /*
    * As you can see, this method is different. It doesn't have body.
    * Using magic (and reflection) Spring will find a user by his email.
    * It will use reflection to get the type of the repository, which is our entity "User", then it will get the table name from the annotation.
    * After that, it will split the name of our method into different parts.
    * The first part is "findBy", which means that it will send SELECT query to our database.
    * Then it will take the second part which is "Email" in this case and it will understand that we want to get user by a given email address.
    * The generated query will look like this "SELECT id, email, full_name, password FROM users WHERE email={parameter}".
    */

}
