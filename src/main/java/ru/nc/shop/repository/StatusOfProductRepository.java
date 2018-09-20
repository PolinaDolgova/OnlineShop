package ru.nc.shop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nc.shop.model.StatusOfProduct;

@Repository
public interface StatusOfProductRepository extends JpaRepository<StatusOfProduct, Long> {
    StatusOfProduct findByName(String Name);
}
