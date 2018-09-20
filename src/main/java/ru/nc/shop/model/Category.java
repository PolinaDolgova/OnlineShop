package ru.nc.shop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    private List<ProductType> productTypes;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Long id){
        this.id = id;
    }

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }
}