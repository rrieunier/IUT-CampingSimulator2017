package fr.iut.view;

import fr.iut.controller.SupplierController;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;

/**
 * Created by theo on 16/03/17.
 */
public class SupplierManagerView extends SubScene{

    private SupplierController controller;

    public SupplierManagerView(SupplierController controller){
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        this.controller = controller;
    }
}
