package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.blog.entity.Category;

/**
 * Created by Admin on 6.12.2016 г..
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
