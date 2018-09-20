package ru.nc.shop.model;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_type_id")
    private Long id;

    @Column(name = "product_type_name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "number")
    private int number;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "zone_id")
    private Long zoneId;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "category_of_product_type",
            joinColumns = @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private List<Category> categories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productTypeOfProduct")
    private Set<Product> products;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "products_attributes",
            joinColumns = @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id"))
    private List<Attribute> attributes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productType")
    private List<Value> values;

    public ProductType(List<Category> categories, Long zoneId) {
        this.zoneId = zoneId;
        this.categories = categories;
    }

    public ProductType(String name, List<Category> categories, List<Product> products, Double price, int number, Long unitId, Long zoneId) {
        this.name = name;
        this.categories = categories;
        this.products = new HashSet<>(products);
        this.price = price;
        this.number = number;
        this.unitId = unitId;
        this.zoneId = zoneId;
    }

    public ProductType() {
    }

    public ProductType(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public ProductType(String name, List<Category> categories,
                       Producer producer, Double price, List<Attribute> attributes, int number, Long unitId, Long zoneId) {
        this.name = name;
        this.categories = categories;
        this.producer = producer;
        this.price = price;
        this.attributes = attributes;
        this.number = number;
        this.unitId = unitId;
        this.zoneId = zoneId;
    }

    @PostConstruct
    public void init() {
        if (this.number != this.products.size()) {
            this.number = this.products.size();
        }
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<Product> products) {
        this.products = new HashSet<>(products);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
