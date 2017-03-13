package fr.iut.persistence.entities;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/17/17.
 */
@Entity
@Table(name = "SUPPLIER_PROPOSE_PRODUCT")
public class SupplierProposeProduct extends EntityModel<Integer> {

    /**
     * Id of this proposition.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Sell price for this proposition.
     */
    @Column(nullable = false)
    private float sellPrice;

    /**
     * Supplier who proposes.
     */
    @ManyToOne(optional = false)
    private Supplier supplier;

    /**
     * Product proposed.
     */
    @ManyToOne(optional = false)
    private Product product;

    public Integer getId() {
        return id;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Product getProduct() {
        return product;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
