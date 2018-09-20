package ru.nc.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nc.shop.model.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Attribute findByName(String name);
}

