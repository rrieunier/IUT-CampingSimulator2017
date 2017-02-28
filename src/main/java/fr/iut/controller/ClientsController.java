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

    public void createClient(){
        InputsListDialog newClientDialog = new InputsListDialog("Nouveau Client");
        newClientDialog.addTextField("Nom");
        newClientDialog.addTextField("Prénom");
        newClientDialog.addTextField("Téléphone");
        newClientDialog.addTextField("Mail");
        Optional<Map<String, String>> newClient_result = newClientDialog.showAndWait();

        Client client = new Client();
        client.setFirstname(newClient_result.get().get("Prénom"));
        client.setLastname(newClient_result.get().get("Nom"));
        client.setPhone(newClient_result.get().get("Téléphone"));
        client.setEmail(newClient_result.get().get("Mail"));

        GenericDAOImpl<Client, Integer> dao = new GenericDAOImpl<>(Client.class);
        dao.open();
        dao.saveOrUpdate(client);
        dao.close();
    }
}
