package fr.iut.persistence.dao;

import fr.iut.controller.ConnectionController;
import fr.iut.persistence.entities.*;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Sydpy on 3/9/17.
 */
public class DummyData {


    public static void main(String[] args) {

        //Crete and persist your dummy datas for test here
        GenericDAO<Spot, Integer> daoSpot = new GenericDAO<>(Spot.class);
        daoSpot.removeAll();
        for (int i = 0; i < 50; i++) {
            Spot spot = new Spot();
            spot.setName("Emplacement " + i);
            spot.setCapacity(2);
            spot.setPointX((double) i);
            spot.setPointY((double) i);
            daoSpot.save(spot);
        }
        ArrayList<Spot> spots = new ArrayList<>();
        spots.addAll(daoSpot.findAll());
        GenericDAO<Client, Integer> daoClient = new GenericDAO<>(Client.class);
        for (int i = 0; i < 50; i++) {
            Client client = new Client();
            client.setFirstname("pre " + i);
            client.setLastname("nom" + i);
            client.setPhone("06000000" + i);
            client.setEmail("reservation" + i + "@camping.oklm");
            daoClient.save(client);
        }
        ArrayList<Client> clients = new ArrayList<>();
        clients.addAll(daoClient.findAll());
        ReservationsDAO dao = new ReservationsDAO();
        for (int i = 0; i < 50; i++) {
            Reservation reservation = new Reservation();
            reservation.setSpot(spots.get(i));
            reservation.setClient(clients.get(i));
            reservation.setStarttime(new Timestamp(System.currentTimeMillis()));
            reservation.setEndtime(new Timestamp(System.currentTimeMillis() + 1));
            reservation.setPersonCount(1);
            dao.save(reservation);
        }
        GenericDAO<Employee, Integer> daoEmployee = new GenericDAO<>(Employee.class);
        for (int i = 0; i < 50; i++) {
            Employee employee = new Employee();
            employee.setFirstName("prenom" + i);
            employee.setLastName("nom" + i);
            employee.setCompleteAddress(i + " rue des Flots Blancs");
            employee.setPhone("06000000" + i);
            if (i % 2 == 0) {
                employee.setEmail(employee.getFirstName() + "." + employee.getLastName() + "@camping.simulator");
                employee.setLogin(employee.getFirstName().charAt(0) + employee.getLastName());
                employee.setPassword(ConnectionController.hash("Employee" + i));
            }
            daoEmployee.save(employee);
        }

        Employee employee = new Employee();
        employee.setFirstName("test");
        employee.setLastName("test");
        employee.setCompleteAddress("42 rue des Flots Blancs");
        employee.setPhone("060000042");
        employee.setEmail(employee.getFirstName() + "." + employee.getLastName() + "@camping.simulator");
        employee.setLogin("test");
        employee.setPassword(ConnectionController.hash("test"));
        daoEmployee.save(employee);

        GenericDAO<Notification, Integer> daoNotifications = new GenericDAO<>(Notification.class);

        for (int i = 0; i < 50; i++) {
            Notification notification = new Notification();
            notification.setTitle("Notification " + i);
            notification.setContent("Coucou je suis la notification ! :)");
            daoNotifications.save(notification);
        }
    }
}
