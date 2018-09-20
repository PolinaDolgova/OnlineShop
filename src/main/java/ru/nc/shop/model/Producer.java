package ru.nc.shop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "producer")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "producer_id")
    private Long id;

    @Column(name = "producer_name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "producer")
    private List<ProductType> productTypes;

    public Producer(){}

    public Producer(String name) {
        this.name = name;
    }
    public Producer(String name, List<ProductType> productTypes) {
        this.name = name;
        this.productTypes = productTypes;
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


