package ru.nc.shop.service;

import ru.nc.shop.model.Category;
import ru.nc.shop.model.ProductType;

import java.util.List;

public interface ProductTypeService {

    void save(ProductType productType);

    ProductType changeCategoryOfProductType(ProductType productType, Category category);

    ProductType findProductTypeById(Long productTypeId);

    Integer createProductType(String name, String categories, String producer, String price, String zone_id, Long unit_id, String[] attributes, String[] values, String image);

    Integer deleteProductType(Long id);

    void modifyProductType(String categories, String price, String zone_id, Long unit_id, String[] attributes, String[] values, ProductType productType, String image);

    List<ProductType> getUnsoldProductTypes();
}
