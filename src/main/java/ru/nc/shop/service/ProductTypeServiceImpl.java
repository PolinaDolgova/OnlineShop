package ru.nc.shop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nc.shop.model.*;
import ru.nc.shop.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ValueRepository valueRepository;

    @Override
    public void save(ProductType productType) {
        productTypeRepository.save(productType);
    }

    @Override
    public ProductType changeCategoryOfProductType(ProductType productType, Category category) {
        List<Category> categoryList = productType.getCategories();

        for (Category categoryOfProductType : categoryList) {
            if (categoryOfProductType.equals(category)) {
                return productType;
            }
        }

        categoryList.add(category);
        productType.setCategories(categoryList);

        return productType;
    }

    public ProductType changePlacementOfProductType(ProductType productType, Long zoneId) {

        if (productType.getZoneId() != zoneId) {
            productType.setZoneId(zoneId);
        }

        return productType;
    }

    @Override
    public ProductType findProductTypeById(Long productTypeId) {
        return productTypeRepository.findProductTypeById(productTypeId);
    }

    private boolean isExistsProductType(String name) {
        return productTypeRepository.findByName(name) != null;
    }


    private Producer addProducer(String producerName) {
        Producer producer;
        if (producerRepository.findByName(producerName) == null) {
            producer = producerRepository.saveAndFlush(new Producer(producerName));
        } else {
            producer = producerRepository.findByName(producerName);
        }
        return producer;
    }

    private List<Category> addCategory(String categories) {
        List<Category> categoryList = new ArrayList<>();
        String[] category = categories.split(",");
        for (int i = 0; i < category.length; i++) {
            String name = category[i].trim();
            if (categoryRepository.findByName(name) == null) {
                categoryList.add(categoryRepository.saveAndFlush(new Category(name)));
            } else {
                categoryList.add(categoryRepository.findByName(name));
            }
        }
        return categoryList;
    }

    private List<Attribute> addAttribute(String[] attributes) {
        List<Attribute> attributeList = new ArrayList<>();
        if (attributes != null) {
            for (int i = 0; i < attributes.length; i++) {
                String name = attributes[i].trim();
                if (attributeRepository.findByName(name) == null) {
                    attributeList.add(attributeRepository.saveAndFlush(new Attribute(name.toLowerCase())));
                } else {
                    attributeList.add(attributeRepository.findByName(name.toLowerCase()));
                }
            }
        }
        return attributeList;
    }

    private List<Value> addValue(String[] values, ProductType productType, List<Attribute> attributes) {
        List<Value> valueList = new ArrayList<>();
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String value = values[i].trim();
                List<Attribute> attributeList = new ArrayList<>();
                attributeList.add(attributes.get(i));
                valueList.add(valueRepository.saveAndFlush(new Value(value, productType, attributeList)));
            }
        }
        return valueList;
    }

    private String generateName(String name, String[] attributes, String[] values) {
        StringBuilder builder = new StringBuilder();
        if (name.indexOf(',') == -1) {
            builder.append(name);
        } else {
            builder.append(name.substring(0, name.indexOf(',')));
        }
        if (attributes != null) {
            builder.append(",");
            for (int i = 0; i < attributes.length; i++) {
                builder.append(attributes[i] + ": " + values[i] + "; ");
            }
        }
        return builder.toString().trim();
    }

    @Override
    public Integer createProductType(String name, String categories, String producerName, String price, String zone_id, Long unit_id, String[] attributes, String[] values, String image) {
        Integer result = 0;
        String productTypeName = generateName(name, attributes, values);
        if (!isExistsProductType(productTypeName)) {
            List<Category> categoryList = addCategory(categories);
            Producer producer = addProducer(producerName);
            List<Attribute> attributesList = addAttribute(attributes);
            ProductType productType = new ProductType(productTypeName, categoryList, producer, Double.parseDouble(price), attributesList, 0, (long) unit_id, Long.parseLong(zone_id));
            productType.setImage(image);
            productTypeRepository.saveAndFlush(productType);
            List<Value> valueList = addValue(values, productType, attributesList);
            result = 1;
        }
        return result;
    }

    @Override
    public Integer deleteProductType(Long id) {
        Integer result = 0;
        if (isExistsProductType(findProductTypeById(id).getName())) {
            List<Value> valueList = valueRepository.findAll();
            for (Value value : valueList) {
                if (value.getProductType().getId().equals(id)) {
                    valueRepository.delete(value.getId());
                }
            }
            productTypeRepository.delete(id);
            result = 1;
        }
        return result;
    }

    @Override
    public void modifyProductType(String categories, String price, String zone_id, Long unit_id, String[] attributes, String[] values, ProductType productType, String image) {
        productType.setCategories(addCategory(categories));
        productType.setPrice(Double.parseDouble(price));
        productType.setZoneId(Long.parseLong(zone_id));
        if (!Objects.equals(image, ""))
            productType.setImage(image);
        productType.setUnitId(unit_id);
        List<Attribute> attributeList = productType.getAttributes();
        if (attributes != null) {
            List<Attribute> newAttribute = addAttribute(attributes);
            attributeList.addAll(newAttribute);
        }
        String[] attributeNames = new String[attributeList.size()];
        for (int i = 0; i < attributeList.size(); i++) {
            attributeNames[i] = attributeList.get(i).getName();
        }
        String name = generateName(productType.getName(), attributeNames, values);
        productType.setName(name);
        productType.setAttributes(attributeList);
        List<Value> valueList = valueRepository.findAll();
        for (Value value : valueList) {
            if (value.getProductType().getId().equals(productType.getId())) {
                valueRepository.delete(value.getId());
            }
        }
        productType.setValues(addValue(values, productType, productType.getAttributes()));
    }

    @Override
    public List<ProductType> getUnsoldProductTypes() {
        List<ProductType> listOfAllProductType = productTypeRepository.findAll();
        List<ProductType> listOfUnsoldProductTypes = new ArrayList<>();
        for (ProductType productType : listOfAllProductType) {
            List<Product> products = productType.getProducts();
            boolean found = false;
            for (Product product : products) {
                if (product.getSalesStatus() == 2L) {
                    found = true;
                    break;
                }
            }
            if (found)
                listOfUnsoldProductTypes.add(productType);
        }
        return listOfUnsoldProductTypes;
    }
}
