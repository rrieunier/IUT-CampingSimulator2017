package fr.iut.view;

import fr.iut.App;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Created by shellcode on 2/3/17.
 */
public class ConnectionView extends StackPane {

    App app;

    public ConnectionView(App app) { //TODO : T'as une instance du controller pour faire un app.tryLogin(user, pass) quand l'utilisateur clique sur le bouton pour se co
        this.app = app;
        //TODO : Maxime tu sais ce qu'il te reste à faire ;)

        Button connectionButton = new Button();
        connectionButton.setText("Connect");
        connectionButton.setOnAction(event -> connectionButtonAction("input login", "input password"));
        getChildren().add(connectionButton);
    }

    private void connectionButtonAction(String username, String password) {
        boolean connected = app.tryLogin(username, password);

        if(connected)
            app.GO_MAMENE_GOOOOOOOOOO();
        else
            ;//TODO : Petit message des familles à l'ancienne ou petit popup c'est toi qui vois mamen, et t'as intérêt de bosser entre 2 pistes de ski. PS : j'espère que tu vas tomber grosse merde
    }
}
