package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.blog.entity.Tag;

/**
 * Created by Admin on 7.12.2016 г..
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByName(String name);
}
