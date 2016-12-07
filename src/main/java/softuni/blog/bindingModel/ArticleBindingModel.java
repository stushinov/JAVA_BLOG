package softuni.blog.bindingModel;

import javax.validation.constraints.NotNull;

/**
 * Created by Admin on 29.11.2016 г..
 *
 * We still need to validate the user input. This is done by creating binding models.
 * The idea behind them is to fill the user input inside and validate it. If it validates, we can use it in our application
 */
public class ArticleBindingModel {

    //. If the user tries to submit our form without data, it will not validate.
    // If you check, you will see that these fields have exactly the same name, as the input fields names in our create form.
    // This is really important. If they have different names Spring won't be able to autofill the binding model.
    @NotNull
    private String title;

    @NotNull
    private String content;


    private Integer categoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
