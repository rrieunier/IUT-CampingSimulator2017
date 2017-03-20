package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Supplier implements EntityModel<Integer> {

    /**
     * Supplier's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Supplier's name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Supplier's phone number.
     */
    @Column(length = 11)
    private String phone;

    /**
     * Supplier's email.
     */
    @Column
    private String email;

    /**
     * Supplier's website.
     */
    @Column
    private String website;

    /**
     * Restockings made by this supplier.
     */
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Restocking> restockings = new HashSet<>();

    /**
     * Product propositions by this supplier.
     */
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<SupplierProposeProduct> supplierProposeProducts = new HashSet<>();

    public Integer getId() {
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

    public void setId(Integer id) {
        this.id = id;
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
