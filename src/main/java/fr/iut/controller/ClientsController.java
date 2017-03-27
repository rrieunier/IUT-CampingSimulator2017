package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Client;
import fr.iut.view.ClientManagerView;
import javafx.scene.SubScene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shellcode on 2/17/17.
 */
public class ClientsController {

    private HomeController homeController;
    private GenericDAO<Client, Integer> daoClient;

    public ClientsController(HomeController homeController) {
        this.homeController = homeController;
    }

    public SubScene getView() {
        return new ClientManagerView(this);
    }

    public List<Client> getAllClients() {

        //fix NPE bug
        if(daoClient == null)
            daoClient = new GenericDAO<>(Client.class);

        System.out.println("finding clients...");

        List<Client> clients = daoClient.findAll(); //May cause NPE

        System.out.println("returning clients");
        return clients;
    }

    public void saveClient(Client client) {
        daoClient.save(client);
    }
}
