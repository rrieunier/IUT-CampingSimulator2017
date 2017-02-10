package fr.iut.model;

import javax.persistence.*;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Supplier_has_Product", schema = "CampingSimulator", catalog = "")
@IdClass(SupplierHasProductEntityPK.class)
public class SupplierHasProductEntity {
    private int idSupplier;
    private int idProduct;
    private double buyPrice;
    private SupplierEntity supplierByIdSupplier;
    private ProductEntity productByIdProduct;

    @Id
    @Column(name = "idSupplier", nullable = false)
    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    @Id
    @Column(name = "idProduct", nullable = false)
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

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

        SupplierHasProductEntity that = (SupplierHasProductEntity) o;

        if (idSupplier != that.idSupplier) return false;
        if (idProduct != that.idProduct) return false;
        if (Double.compare(that.buyPrice, buyPrice) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idSupplier;
        result = 31 * result + idProduct;
        temp = Double.doubleToLongBits(buyPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idSupplier", referencedColumnName = "idSupplier", nullable = false)
    public SupplierEntity getSupplierByIdSupplier() {
        return supplierByIdSupplier;
    }

    public void setSupplierByIdSupplier(SupplierEntity supplierByIdSupplier) {
        this.supplierByIdSupplier = supplierByIdSupplier;
    }

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct", nullable = false)
    public ProductEntity getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(ProductEntity productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }
}
