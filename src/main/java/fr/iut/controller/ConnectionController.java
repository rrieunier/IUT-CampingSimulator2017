package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Employee;
import fr.iut.persistence.entities.Log;
import fr.iut.persistence.exception.InvalidLoginPasswordException;
import fr.iut.view.ConnectionView;
import javafx.scene.Scene;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;


public class ConnectionController {

    private App app;

    private EmployeeDAO daoEmployee = new EmployeeDAO();

    public ConnectionController(App app) {
        this.app = app;
    }

    public Scene getView() {
        return new ConnectionView(this);
    }

    public boolean tryLogin(String username, String password) {

        if(username.length() == 0 || password.length() == 0)
            return false;

        Employee employee = daoEmployee.findByLogin(username);

        if(employee == null)
            return false;

        try {
            daoEmployee.connectUser(username, hash(password));

            GenericDAO<Log, Integer> logDao = new GenericDAO<>(Log.class);
            Log connLog = new Log();
            connLog.setDatetime(new Timestamp(System.currentTimeMillis()));
            connLog.setAction("[CONNECTION] by " + username);
            connLog.setEmployee(employee);
            logDao.save(connLog);

            return true;
        } catch (InvalidLoginPasswordException e) {
            return false;
        }
    }

    public void finish() {
        app.switchState(State.HOME);
    }

    public Employee getConnectedUser() {
        return EmployeeDAO.getConnectedUser();
    }

    public void logout() {
        daoEmployee.disconnectUser();
    }

    public static String hash(String password) {
        StringBuffer hexString = new StringBuffer();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = md.digest(password.getBytes());

        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10)
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            else
                hexString.append(Integer.toHexString(0xFF & hash[i]));
        }

        return hexString.toString();
    }
}
