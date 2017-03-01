package fr.iut.controller;

import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Client;
import fr.iut.view.ClientManagerView;
import fr.iut.view.InputsListDialog;
import javafx.scene.SubScene;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shellcode on 2/17/17.
 */
public class ClientsController implements ControllerInterface {

    HomeController homeController;
    ClientManagerView clientManagerView = new ClientManagerView(this);
    GenericDAOImpl<Client, Integer> daoClient = new GenericDAOImpl<>(Client.class);

    public ClientsController(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public SubScene getView() {
        return clientManagerView;
    }

    @Override
    public void finish() {

    }

    public ArrayList<Client> getClients() {

        ArrayList<Client> stub = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            Client client = new Client();
            client.setFirstname("Client");
            client.setLastname("" + i);
            client.setPhone("06 00 00 00 " + i);
            client.setEmail("client" + i + "@camping.oklm");
            stub.add(client);
        }

        return stub;
    }

    public void saveClient(Client client) {
        daoClient.open();
        daoClient.saveOrUpdate(client);
        daoClient.close();
    }
}
