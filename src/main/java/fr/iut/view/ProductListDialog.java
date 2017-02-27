package fr.iut.view;

import fr.iut.App;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by theo on 27/02/17.
 */
public class ProductListDialog extends Dialog<ArrayList<String>> {

    public static final double PRODUCTSLIST_WIDTH = App.SCREEN_W / 3;
    public static final double PRODUCTSLIST_HEIGHT = App.SCREEN_H / 3;

    private ArrayList<Pair<Text, RadioButton>> arrayListProducts = new ArrayList<>();
    private VBox wrapper = new VBox();
    private VBox productsWrapper = new VBox();
    private int iterator_box = 0;

    private void add(String name){
        Text text = new Text(name);
        text.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        text.setFill(Color.WHITESMOKE);

        RadioButton radioButton = new RadioButton();

        Pair<Text, RadioButton> pair = new Pair<>(text, radioButton);
        arrayListProducts.add(pair);

        BorderPane borderPane = new BorderPane();
        borderPane.setMinWidth(PRODUCTSLIST_WIDTH);

        if(iterator_box++ % 2 == 1)
            borderPane.setStyle("-fx-background-color: #336699;");
        else
            borderPane.setStyle("-fx-background-color: #0F355C;");

        BorderPane.setMargin(text, new Insets(20,0,20,100));

        BorderPane.setMargin(radioButton, new Insets(20, 100, 20, 0));
        BorderPane.setAlignment(radioButton, Pos.CENTER);

        borderPane.setLeft(text);
        borderPane.setRight(radioButton);
        productsWrapper.getChildren().add(borderPane);
    }

    public ProductListDialog(){
        setTitle("Liste des produits");
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        ScrollPane scrollPane = new ScrollPane(productsWrapper);
        scrollPane.setMaxHeight(PRODUCTSLIST_HEIGHT);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        HeaderView header = new HeaderView("Liste des produits");
        header.setMinWidth(PRODUCTSLIST_WIDTH);
        wrapper.getChildren().add(header);

        ButtonType okButtonType = new ButtonType("Commander", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        add("Haricot");
        add("c'est");
        add("beau la vie");
        add("pour les grands");
        add("et les petits");
        for (int i = 0; i < 20; i++) {
            add(""+i);
        }

        productsWrapper.setSpacing(3);
        wrapper.getChildren().add(scrollPane);
        dialogPane.setContent(wrapper);

        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hbox = (HBox) dialogPane.lookup(".container");
        hbox.getChildren().add(spacer);

        setResultConverter((ButtonType dialogButton) ->{
            ArrayList<String> arrayList = new ArrayList<>();

            if(dialogButton == okButtonType){
                for (int i = 0; i < arrayListProducts.size(); i++) {
                    if(arrayListProducts.get(i).getValue().isSelected()){
                        arrayList.add(arrayListProducts.get(i).getKey().getText().toString());
                    }
                }
            }
            return arrayList;
        });    }
}
