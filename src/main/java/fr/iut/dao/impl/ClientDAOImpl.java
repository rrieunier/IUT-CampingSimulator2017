package fr.iut.dao.impl;

import fr.iut.model.Client;

import javax.transaction.Transactional;

/**
 * Created by Sydpy on 2/15/17.
 */
@Transactional(rollbackOn = RuntimeException.class)
public class ClientDAOImpl extends GenericDAOImpl<Client,Integer> {

    public ClientDAOImpl() {
        super(Client.class, Integer.class);
    }
}
