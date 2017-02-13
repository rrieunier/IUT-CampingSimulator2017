package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Supplier", schema = "CampingSimulator", catalog = "")
public class SupplierEntity {
    private String name;
    private String phone;
    private String email;
    private String website;
    private int id;
    private Collection<RestockingEntity> restockings;
    private Collection<SupplierHasProductEntity> supplierHasProducts;

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

        SupplierEntity that = (SupplierEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;

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

    @OneToMany(mappedBy = "supplier")
    public Collection<RestockingEntity> getRestockings() {
        return restockings;
    }

    public void setRestockings(Collection<RestockingEntity> restockings) {
        this.restockings = restockings;
    }

    @OneToMany(mappedBy = "supplier")
    public Collection<SupplierHasProductEntity> getSupplierHasProducts() {
        return supplierHasProducts;
    }

    public void setSupplierHasProducts(Collection<SupplierHasProductEntity> supplierHasProducts) {
        this.supplierHasProducts = supplierHasProducts;
    }
}
