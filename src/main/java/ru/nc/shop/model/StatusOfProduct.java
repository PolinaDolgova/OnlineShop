package ru.nc.shop.model;

import javax.persistence.*;

@Entity
@Table(name = "status")
public class StatusOfProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long id;

    @Column(name = "status_name")
    private String name;



    public StatusOfProduct() {
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
}
