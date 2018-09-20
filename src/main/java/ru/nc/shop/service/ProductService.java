package ru.nc.shop.service;


import ru.nc.shop.model.Product;

public interface ProductService {
    void save(Product p);

    Product changeStatusOfProduct(Product product, Long salesStatusId, Long logisticsStatusId);

    Product findProductById(Long product_id);

    public Product getUnsoldProductByIdOfProductType(Long id);

    void updateProduct(Product product);
}