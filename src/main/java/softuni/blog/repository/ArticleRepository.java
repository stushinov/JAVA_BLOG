package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.blog.entity.Article;

/**
 * Created by Admin on 29.11.2016 г..
 *
 * Spring gives us really easy way of communicating with the database.
 * It's called repository. Each repository gives us basic functions for working with given entity in the database.
 */
public interface ArticleRepository extends JpaRepository<Article, Integer>{


}
