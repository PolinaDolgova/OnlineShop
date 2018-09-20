package ru.nc.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nc.shop.model.Value;

public interface ValueRepository extends JpaRepository<Value, Long> {
    Value findByValue(String value);
}

