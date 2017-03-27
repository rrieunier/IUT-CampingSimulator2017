package fr.iut.view;

import fr.iut.App;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Product;
import fr.iut.persistence.entities.SupplierProposeProduct;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theo on 27/02/17.
 */
public class ProductListDialog extends Dialog<ArrayList<Pair<Product, Float>>> {

    public static final double PRODUCTSLIST_WIDTH = App.SCREEN_W / 3;
    public static final double PRODUCTSLIST_HEIGHT = App.SCREEN_H / 3;

    private ArrayList<Pair<Pair<Product, TextField>, RadioButton>> arrayListProducts = new ArrayList<>();
    private VBox productsWrapper;
    private int iterator_box = 0;

    public void add(Product p){

        GridPane gridPane = new GridPane();
        gridPane.setMinWidth(PRODUCTSLIST_WIDTH);
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(30);
            gridPane.getColumnConstraints().add(column);
        }

        Text text = new Text(p.getName());
        text.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        text.setFill(Color.WHITESMOKE);

        TextField price = new TextField();
        price.setMaxWidth(PRODUCTSLIST_WIDTH/10);
        price.setMaxHeight(PRODUCTSLIST_HEIGHT/15);
        price.setPromptText("Prix");
        price.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px;");

        RadioButton radioButton = new RadioButton();

        Pair productTextFieldPair = new Pair<>(p, price);
        Pair<Pair<Product, TextField>, RadioButton> pairRadioButtonPair = new Pair<>(productTextFieldPair, radioButton);
        arrayListProducts.add(pairRadioButtonPair);


        if(iterator_box++ % 2 == 1)
            gridPane.setStyle("-fx-background-color: #336699;");
        else
            gridPane.setStyle("-fx-background-color: #0F355C;");


        gridPane.setPadding(new Insets(20));
        GridPane.setHalignment(text, HPos.LEFT);
        GridPane.setHalignment(price, HPos.CENTER);
        GridPane.setHalignment(radioButton, HPos.RIGHT);
        gridPane.add(text,0,0);
        gridPane.add(price,1,0);
        gridPane.add(radioButton,2,0);

        productsWrapper.getChildren().add(gridPane);
    }

    public void createProducts(){
        GenericDAO<Product, Integer> daoProduct = new GenericDAO<>(Product.class);
        List<Product> products = daoProduct.findAll();
        for (Product p: products) {
            add(p);
        }
    }

    public void checkProducts(ArrayList<SupplierProposeProduct> supplierProposeProducts){
        for (int i = 0; i < supplierProposeProducts.size(); i++) {
            for (int j = 0; j < arrayListProducts.size(); j++) {
                if (supplierProposeProducts.get(i).getProduct() == arrayListProducts.get(j).getKey().getKey()){
                    arrayListProducts.get(j).getValue().setSelected(true);
                    arrayListProducts.get(j).getKey().getValue().setText(Float.toString(supplierProposeProducts.get(i).getSellPrice()));
                }
            }
        }
    }

    public ProductListDialog(){
        setTitle("Liste des produits");
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        productsWrapper = new VBox();
        ScrollPane scrollPane = new ScrollPane(productsWrapper);
        scrollPane.setMaxHeight(PRODUCTSLIST_HEIGHT);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        HeaderView header = new HeaderView("Liste des produits");
        header.setMinWidth(PRODUCTSLIST_WIDTH);
        VBox wrapper = new VBox();
        wrapper.getChildren().add(header);

        ButtonType okButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        createProducts();

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
            ArrayList<Pair<Product, Float>> arrayList = new ArrayList<>();

            if(dialogButton == okButtonType){
                for (int i = 0; i < arrayListProducts.size(); i++) {
                    if(arrayListProducts.get(i).getValue().isSelected()){
                        Pair p = new Pair(arrayListProducts.get(i).getKey().getKey(), Float.parseFloat(arrayListProducts.get(i).getKey().getValue().getText().toString()));
                        arrayList.add(p);
                    }
                }
            }
            return arrayList;
        });
    }
}
