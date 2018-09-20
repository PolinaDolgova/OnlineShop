package ru.nc.shop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "values")
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "value_id")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "Attributes_Values",
            joinColumns = @JoinColumn(name = "value_id", referencedColumnName = "value_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id"))
    private List<Attribute> attributes;

    public Value(){}

    public Value(String name) {
        this.value = name;
    }
    public Value(String value, ProductType productType, List<Attribute> attributes) {
        this.value = value;
        this.productType = productType;
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}