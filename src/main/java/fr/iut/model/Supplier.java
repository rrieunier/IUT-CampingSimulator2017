package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class Supplier {
    private String name;
    private String phone;
    private String email;
    private String website;
    private int id;
    private Collection<Restocking> restockingsById;
    private Collection<SupplierHasProduct> supplierHasProductsById;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "website", nullable = true, length = 45)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        if (id != supplier.id) return false;
        if (name != null ? !name.equals(supplier.name) : supplier.name != null) return false;
        if (phone != null ? !phone.equals(supplier.phone) : supplier.phone != null) return false;
        if (email != null ? !email.equals(supplier.email) : supplier.email != null) return false;
        if (website != null ? !website.equals(supplier.website) : supplier.website != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public Collection<Restocking> getRestockingsById() {
        return restockingsById;
    }

    public void setRestockingsById(Collection<Restocking> restockingsById) {
        this.restockingsById = restockingsById;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public Collection<SupplierHasProduct> getSupplierHasProductsById() {
        return supplierHasProductsById;
    }

    public void setSupplierHasProductsById(Collection<SupplierHasProduct> supplierHasProductsById) {
        this.supplierHasProductsById = supplierHasProductsById;
    }
}
