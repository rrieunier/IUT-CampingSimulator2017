package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "SUPPLIER")
public class Supplier {

    @Id @GeneratedValue
    @Column
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String website;

    @OneToMany(mappedBy = "supplier")
    private Set<Restocking> restockings = new HashSet<>();

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierProposeProduct> supplierProposeProducts = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public Set<Restocking> getRestockings() {
        return restockings;
    }

    public Set<SupplierProposeProduct> getSupplierProposeProducts() {
        return supplierProposeProducts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setRestockings(Set<Restocking> restockings) {
        this.restockings = restockings;
    }

    public void setSupplierProposeProducts(Set<SupplierProposeProduct> supplierProposeProducts) {
        this.supplierProposeProducts = supplierProposeProducts;
    }
}
