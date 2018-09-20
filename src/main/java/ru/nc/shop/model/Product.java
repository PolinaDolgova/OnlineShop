package ru.nc.shop.model;

import javax.persistence.*;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "product_id")
    private Long id;

    @Column(name = "sales_status")
    private Long salesStatus;

    @Column(name = "logistics_status")
    private Long logisticsStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productTypeOfProduct;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "order_id")
    private Order currentOrder;

    public Product(Long productId,ProductType productType, Long salesStatus, Long logisticsStatus) {
        this.id = productId;
        this.productTypeOfProduct = productType;
        this.salesStatus = salesStatus;
        this.logisticsStatus = logisticsStatus;
    }

    public Product(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productTypeOfProduct;
    }

    public void setProductType(ProductType productTypeOfProduct) {
        this.productTypeOfProduct = productTypeOfProduct;
    }

    public Long getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(Long salesStatus) {
        this.salesStatus = salesStatus;
    }

    public Long getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(Long logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
}