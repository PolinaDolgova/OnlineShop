package ru.nc.shop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attribute_id")
    private Long id;

    @Column(name = "attribute_name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "attributes")
    private List<ProductType> productTypes;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "attributes")
    private List<Value> values;

    public Attribute(){}

    public Attribute(String name) {
        this.name = name;
    }

    public Attribute(String name, List<ProductType> productTypes, List<Value> values) {
        this.name = name;
        this.productTypes = productTypes;
        this.values = values;
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

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}
