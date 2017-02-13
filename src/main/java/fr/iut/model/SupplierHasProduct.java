package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Supplier_has_Product", schema = "CampingSimulator", catalog = "")
@IdClass(SupplierHasProductPK.class)
public class SupplierHasProduct {
    private double buyPrice;
    private Supplier supplierBySupplierId;
    private Product productByProductId;
    private int supplierId;
    private int productId;

    @Basic
    @Column(name = "buy_price", nullable = false, precision = 0)
    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierHasProduct that = (SupplierHasProduct) o;

        if (Double.compare(that.buyPrice, buyPrice) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(buyPrice);
        return (int) (temp ^ (temp >>> 32));
    }

    @ManyToOne
    @JoinColumn(name = "Supplier_id", referencedColumnName = "id", nullable = false)
    public Supplier getSupplierBySupplierId() {
        return supplierBySupplierId;
    }

    public void setSupplierBySupplierId(Supplier supplierBySupplierId) {
        this.supplierBySupplierId = supplierBySupplierId;
    }

    @ManyToOne
    @JoinColumn(name = "Product_id", referencedColumnName = "id", nullable = false)
    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
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
}
