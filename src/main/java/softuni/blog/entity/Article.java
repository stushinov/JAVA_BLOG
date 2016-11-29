package softuni.blog.entity;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;

/**
 * Created by Admin on 29.11.2016 г..
 */

//Now we need to tell Hibernate that this is an entity
@Entity
//Now that our class is an entity we need to give our database proper table name
@Table(name = "articles")
public class Article {
    
         /*
        We will use this constructor to create articles easily.
        However, we need to create another empty constructor for Hibernate
        */
    public Article(Integer id, String title, User author){
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Article() {}


    //The next important thing is the table columns. We need columns for id, title, content and author
    private Integer id;

    private String title;

    private String content;

    private User author;




    //The id column will be the primary key in our database and as such we need to use the "@Id" annotation.
    //The "@GeneratedValue" annotation tells Hibernate that the database should generate the values automatically.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    //The "@Column" annotation gives us many useful features.
    //For this case however, we only want to tell Hibernate that this column can't be empty.
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    /*
        Here we are again making the field required.
        By default, fields of type "String" will use the database type "VARCHAR(255)".
        This type is string limited to 255 symbols.
        We can change the limit, but we can't be sure how long the content of an article will be.
        That's why we will change the database type to "text". The "text" type doesn’t have limit on its length.
    */
    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
