package ru.nc.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nc.shop.model.ProductType;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    ProductType findProductTypeById(Long productTypeId);
    ProductType findByName(String Name);
}