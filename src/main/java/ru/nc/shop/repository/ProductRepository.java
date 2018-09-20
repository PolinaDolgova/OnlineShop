package ru.nc.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nc.shop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long productId);
}