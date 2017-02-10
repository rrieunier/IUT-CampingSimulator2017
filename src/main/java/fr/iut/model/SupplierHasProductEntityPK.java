package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by roman on 10/02/17.
 */
public class SupplierHasProductEntityPK implements Serializable {
    private int idSupplier;
    private int idProduct;

    @Column(name = "idSupplier", nullable = false)
    @Id
    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    @Column(name = "idProduct", nullable = false)
    @Id
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierHasProductEntityPK that = (SupplierHasProductEntityPK) o;

        if (idSupplier != that.idSupplier) return false;
        if (idProduct != that.idProduct) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSupplier;
        result = 31 * result + idProduct;
        return result;
    }
}
