package ru.nc.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nc.shop.model.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Producer findByName(String name);
}
