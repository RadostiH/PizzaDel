package sapproject.pizzadelivery.domain.entities;

import sapproject.pizzadelivery.web.controllers.BaseController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;

    public Category() {
    }


    @Column(name = "category_name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
