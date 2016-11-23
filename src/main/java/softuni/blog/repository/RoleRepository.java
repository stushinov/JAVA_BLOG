package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.blog.entity.Role;

/**
 * Created by Admin on 23.11.2016 г..
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    /*
    *  This will get us the role with given name.
    *  It is almost the same as the method in the UserRepository, but the criteria and return types are different.
    */
}
