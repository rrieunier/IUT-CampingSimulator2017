package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.view.ConnectionView;
import javafx.scene.Scene;


public class ConnectionController implements ControllerInterface {

    private App app;
    private ConnectionView connectionView = new ConnectionView(this);
    private String connectedUser = null;

    public ConnectionController(App app) {
        this.app = app;
    }

    @Override
    public Scene getView() {
        return connectionView;
    }

    @SuppressWarnings("unused")
    public boolean tryLogin(String username, String password) {

        //if(connectionSucceed)
            connectedUser = username;

        //TODO : demander à la base de données si les identifiants sont bons, s'il ne le sont pas, la fonction retourne false et ConnectionView affiche un message d'erreur
        return true;
    }

    @Override
    public void finish() {
        //TODO : remetrre State.MAP_CREATOR
        app.switchState(State.HOME);
    }

    public String getConnectedUser() {
        return connectedUser;
    }

    public void logout() {
        connectedUser = null;
        connectionView = new ConnectionView(this);
    }
}
