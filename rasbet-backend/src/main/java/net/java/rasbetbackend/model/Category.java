package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idCategory")
        })
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idCategory;
    @NotBlank
    private String name;

    public Category() {

    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(int idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }
}
