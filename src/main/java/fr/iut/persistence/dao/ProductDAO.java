package fr.iut.persistence.dao;

import fr.iut.persistence.entities.Product;

import javax.transaction.Transactional;

/**
 * Created by Sydpy on 3/16/17.
 */
public class ProductDAO extends GenericDAO<Product, Integer> {


    /**
     * Constructor.
     */
    public ProductDAO() {
        super(Product.class);
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public Product update(Product entity) {

        String query = "update Product set " +
                "stock = :stock, " +
                "criticalQuantity = :criticalQuantity, " +
                "name = :name, " +
                "sellPrice = :sellPrice " +
                "where id = :id";

        session.createQuery(query)
                .setParameter("stock", entity.getStock())
                .setParameter("criticalQuantity", entity.getCriticalQuantity())
                .setParameter("name", entity.getName())
                .setParameter("sellPrice", entity.getSellPrice())
                .setParameter("id", entity.getId())
                .executeUpdate();


        return entity;
    }
}
