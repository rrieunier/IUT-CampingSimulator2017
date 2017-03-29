package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Client;
import fr.iut.view.ClientManagerView;
import javafx.scene.SubScene;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by shellcode on 2/17/17.
 */
public class ClientsController {

    private HomeController homeController;
    private GenericDAO<Client, Integer> daoClient = new GenericDAO<>(Client.class);
    private ArrayList<Client> clients = new ArrayList<>();

    public ClientsController(HomeController homeController) {
        this.homeController = homeController;
    }

    public SubScene getView() {
        return new ClientManagerView(this);
    }

    public void createClients() {
        clients = (ArrayList<Client>) daoClient.findAll();
    }

    public void sortClients(int sort_options) {
        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                int result;
                switch (sort_options){
                    case 1:
                        result = o2.getLastname().toLowerCase().compareTo(o1.getLastname().toLowerCase());
                        break;
                    case 2:
                        result = o1.getFirstname().compareTo(o2.getFirstname());
                        break;
                    case 3:
                        result = o2.getFirstname().compareTo(o1.getFirstname());
                        break;
                    default:
                        result = o1.getLastname().toLowerCase().compareTo(o2.getLastname().toLowerCase());
                        break;
                }
                return result;
            }
        });

    }

    public ArrayList<Client> getClients() { return clients; }

    public void saveClient(Client client) {
        daoClient.save(client);
    }

    public void updateClient(Client client) { daoClient.update(client); }

    public void eraseClient(Client client) { daoClient.remove(client); }

    public void displayReservations(Client currentClient) {
        homeController.displayReservations(currentClient);
    }
}
