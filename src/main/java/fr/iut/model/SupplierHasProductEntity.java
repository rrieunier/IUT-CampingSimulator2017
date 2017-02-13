package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Supplier_has_Product", schema = "CampingSimulator", catalog = "")
@IdClass(SupplierHasProductEntityPK.class)
public class SupplierHasProductEntity {
    private double buyPrice;
    private int supplierId;
    private int productId;
    private SupplierEntity supplier;
    private ProductEntity product;

    @Basic
    @Column(name = "buy_price", nullable = false, precision = 0)
    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Id
    @Column(name = "Supplier_id", nullable = false)
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Id
    @Column(name = "Product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierHasProductEntity that = (SupplierHasProductEntity) o;

        if (Double.compare(that.buyPrice, buyPrice) != 0) return false;
        if (supplierId != that.supplierId) return false;
        if (productId != that.productId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(buyPrice);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + supplierId;
        result = 31 * result + productId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Supplier_id", referencedColumnName = "id", nullable = false)
    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    @ManyToOne
    @JoinColumn(name = "Product_id", referencedColumnName = "id", nullable = false)
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
