package ru.nc.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.shop.model.Product;
import ru.nc.shop.model.ProductType;
import ru.nc.shop.model.StatusOfProduct;
import ru.nc.shop.repository.ProductRepository;
import ru.nc.shop.repository.StatusOfProductRepository;

import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    StatusOfProductRepository statusOfProductRepository;

    @Override
    public Product changeStatusOfProduct(Product product, Long salesStatusId, Long logisticsStatusId) {
        if (logisticsStatusId == null) {
            product.setSalesStatus(salesStatusId);
        } else if (salesStatusId == null) {
            product.setLogisticsStatus(logisticsStatusId);
        } else {
            product.setSalesStatus(salesStatusId);
            product.setLogisticsStatus(logisticsStatusId);
        }

        return product;
    }

    @Override
    public void save(Product p) {
        productRepository.save(p);
    }


    @Override
    public Product findProductById(Long productId) {

        return productRepository.findProductById(productId);
    }

    @Override
    public Product getUnsoldProductByIdOfProductType(Long id) {
        Product unsoldProduct = null;
        ProductType productType = productTypeService.findProductTypeById(id);
        StatusOfProduct unsold = statusOfProductRepository.findByName("Unsold");
        for (Product product : productType.getProducts()) {
            if (Objects.equals(product.getSalesStatus(), unsold.getId())) {
                unsoldProduct = product;
                break;
            }
        }
        return unsoldProduct;
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        Product oldProduct = productRepository.findProductById(product.getId());
        oldProduct.setCurrentOrder(product.getCurrentOrder());
        oldProduct.setSalesStatus(product.getSalesStatus());
        oldProduct.setLogisticsStatus(product.getLogisticsStatus());
    }
}
