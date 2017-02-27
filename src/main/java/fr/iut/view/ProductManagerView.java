package fr.iut.view;


import fr.iut.App;
import fr.iut.controller.HomeController;
import fr.iut.persistence.entities.Product;
import javafx.beans.NamedArg;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * the product manager screen
 */
public class ProductManagerView extends SubScene {
    /**
     * instance of the controller
     */
    private HomeController controller;
    /**
     * local list of products
     */
    private ArrayList<Product> products_list;
    /**
     * list of the product shown on the left list
     */
    private ArrayList<Product> shown_list = new ArrayList<>();
    /**
     * product (stackpane) currently selected
     */
    private StackPane lastClicked;
    /**
     * object corespondance of the selected stackpane
     */
    private Product lastClickedValue;
    /**
     * vbox list of products (stackpane)
     */
    private VBox products_box;
    /**
     * details of the selected product
     */
    private VBox details = new VBox();
    /**
     * grid containing attributes of the selected product
     */
    private GridPane grid = new GridPane();

    /**
     * @param controller
     */
    public ProductManagerView(@NamedArg("controller") HomeController controller) {
        super(new VBox(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        this.controller = controller;

        VBox wrapper = (VBox) getRoot();
        wrapper.setMaxSize(HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        wrapper.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        wrapper.setPadding(new Insets(0));
        wrapper.setLayoutY(0);

        wrapper.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des stocks");

        HBox sort_options = new HBox();
        Button add_product = new Button("+");
        add_product.setTooltip(new Tooltip("Ajouter un nouveau produit..."));
        add_product.getStylesheets().add(new File("res/style.css").toURI().toString());
        add_product.getStyleClass().add("record-sales");
        ObservableList<String> options =
                FXCollections.observableArrayList("Nom (alphabétique)", "Nom (alphabétique inverse)", "Quantité (croissante)",
                        "Quantité (décroissante)", "Prix (croissant)", "Prix (décroissant)");
        ComboBox<String> sort_by = new ComboBox<>(options);
        Label sort_by_label = new Label("Tri par: ");
        sort_by_label.setLabelFor(sort_by);

        HBox search_bar = new HBox();
        TextField search_field = new TextField();
        Label search_label = new Label("Rechercher: ");
        search_label.setLabelFor(search_field);

        HBox body = new HBox();
        body.setMinWidth(HomeView.TAB_CONTENT_W * 19 / 20);

        products_box = new VBox();
        products_box.setSpacing(0);
        products_box.setPrefWidth(HomeView.TAB_CONTENT_W / 4);
        VBox.setVgrow(products_box, Priority.ALWAYS);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(products_box);
        scrollPane.setMinSize(products_box.getPrefWidth(), products_box.getPrefHeight());

        details.getStylesheets().add(new File("res/style.css").toURI().toString());
        details.getStyleClass().add("product-detail");
        details.setPrefWidth(3 * HomeView.TAB_CONTENT_W / 4);
        details.setMaxHeight(17 * HomeView.TAB_CONTENT_H / 19);

        HeaderView details_header = new HeaderView("Détails du produit");
        grid.setGridLinesVisible(true);
        grid.getStylesheets().add(new File("res/style.css").toURI().toString());
        grid.getStyleClass().add("stock-grid");

        HBox buttons = new HBox();
        buttons.setSpacing(HomeView.TAB_CONTENT_W / 8);
        for (int i = 0; i < 2; i++) {
            Button button = new Button();
            if (i == 0) {
                button.setText("Gérer fournisseurs");
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
            } else {
                button.setText("Réaprovisionner");
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
            }
            button.setPrefWidth(HomeView.TAB_CONTENT_W / 6);
            button.getStylesheets().add(new File("res/style.css").toURI().toString());
            button.getStyleClass().add("record-sales");
            buttons.getChildren().add(button);
        }

        details.getChildren().addAll(details_header, grid, buttons);
        VBox.setMargin(buttons, new Insets(HomeView.TAB_CONTENT_H / 15, 0, 0, HomeView.TAB_CONTENT_W / 9));

        buildProductsList(0, "", true);

        lastClicked = (StackPane) products_box.getChildren().get(0);
        lastClicked.setStyle("-fx-background-color: #ff6600;");
        lastClickedValue = shown_list.get(0);

        sort_options.setSpacing(10);
        sort_by_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        sort_by.getSelectionModel().select(0);
        sort_by.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buildProductsList(sort_by.getSelectionModel().getSelectedIndex(), search_field.getText(), false);
            }
        });
        search_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        search_field.setPrefWidth(HomeView.TAB_CONTENT_W / 7);
        search_field.setPromptText("Nom du produit");
        search_field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    buildProductsList(sort_by.getSelectionModel().getSelectedIndex(), search_field.getText(), false);
                    search_field.clear();
                }
            }
        });
        add_product.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO: dialog
            }
        });
        sort_options.getChildren().addAll(add_product, sort_by_label, sort_by);
        search_bar.getChildren().addAll(search_label, search_field);

        sort_options.setAlignment(Pos.CENTER);
        search_bar.setAlignment(Pos.CENTER);

        header.setLeft(sort_options);
        header.setRight(search_bar);
        StackPane.setMargin(sort_options, new Insets(5, 0, 0, 0));

        body.getChildren().addAll(scrollPane, details);
        wrapper.getChildren().addAll(header, body);

        buildDetails();
    }

    /**
     * @param sort_option selected sort method to sort the product list
     * @param search specified value to filter product list items
     * @param refresh fetch products in database or not
     */
    private void buildProductsList(@NamedArg("sort_option") int sort_option,
                                   @NamedArg("search_value") String search,
                                   @NamedArg("refresh") boolean refresh) {

        if (refresh) {
            products_list = controller.getProductsList();
        }

        products_list.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                double result = 0;
                switch (sort_option) {
                    case 1:
                        result = o2.getName().compareTo(o1.getName());
                        break;
                    case 2:
                        result = o1.getStock() - o2.getStock();
                        break;
                    case 3:
                        result = o2.getStock() - o1.getStock();
                        break;
                    case 4:
                        result = o1.getSellPrice() - o2.getSellPrice();
                        break;
                    case 5:
                        result = o2.getSellPrice() - o1.getSellPrice();
                        break;
                    default:
                        result = o1.getName().compareTo(o2.getName());
                        break;
                }
                return (int) result;
            }
        });

        products_box.getChildren().clear();
        shown_list.clear();

        for (Product p : products_list) {
            if (p.getName().contains(search)) {
                shown_list.add(p);

                StackPane pane = new StackPane();
                VBox product_wrapper = new VBox();
                HBox informations = new HBox();

                Text name = new Text(p.getName());
                Text stock = new Text("Quantité: " + String.valueOf(p.getStock()));
                Text price = new Text("Prix: " + String.valueOf(p.getSellPrice()));

                name.setFill(Color.WHITESMOKE);
                stock.setFill(Color.WHITESMOKE);
                price.setFill(Color.WHITESMOKE);

                informations.setSpacing(10);
                informations.setAlignment(Pos.CENTER);
                informations.getChildren().addAll(stock, price);

                VBox.setMargin(name, new Insets(15, 0, 5, 0));
                VBox.setMargin(informations, new Insets(0, 0, 15, 0));
                product_wrapper.getChildren().addAll(name, informations);
                product_wrapper.setPadding(new Insets(0, 10, 10, 10));

                pane.getChildren().add(product_wrapper);

                if (products_box.getChildren().size() % 2 == 1)
                    pane.setStyle("-fx-background-color: #336699;");
                else
                    pane.setStyle("-fx-background-color: #0F355C;");

                pane.setOnMouseClicked(event -> {
                    if (lastClicked != null) {
                        if (products_box.getChildren().indexOf(lastClicked) % 2 == 1)
                            lastClicked.setStyle("-fx-background-color: #336699;");
                        else
                            lastClicked.setStyle("-fx-background-color: #0F355C;");
                    }
                    pane.setStyle("-fx-background-color: #ff6600;");
                    lastClicked = pane;
                    lastClickedValue = shown_list.get(products_box.getChildren().indexOf(pane));
                    actualiseDetails();
                });

                products_box.getChildren().add(pane);
            }
        }
    }

    /**
     * build the details grid (structure)
     */
    private void buildDetails() {
        String[] labels = {"Nom", "En stock", "Quantité critique", "Livré le"};
        for (int i = 0; i < 2; i++) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPrefWidth(details.getPrefWidth() / 2);
            grid.getColumnConstraints().add(constraint);
        }
        for (int i = 0; i < labels.length; i++) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPrefHeight(details.getMaxHeight() / 6);
            grid.getRowConstraints().add(constraint);
        }
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i] + ":");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-label");
            grid.addRow(i, label);
            GridPane.setMargin(label, new Insets(0, 0, 0, 30));
        }
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(".");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-field");
            grid.add(label, 1, i);
            GridPane.setMargin(label, new Insets(0, 0, 0, 30));
        }

        actualiseDetails();
    }

    /**
     * fill the details grid depending on the selected product
     */
    private void actualiseDetails() {
        for (Node node : grid.getChildren()) {
            if (node instanceof Label && GridPane.getColumnIndex(node) == 1) {
                switch (GridPane.getRowIndex(node)) {
                    case 0:
                        ((Label) node).setText(lastClickedValue.getName());
                        break;
                    case 1:
                        ((Label) node).setText(String.valueOf(lastClickedValue.getStock()));
                        break;
                    case 2:
                        ((Label) node).setText("TODO GET WITH DAO");
                        break;
                    case 3:
                        ((Label) node).setText("TODO GET WITH DAO");
                        break;
                }
            }
        }
    }
}
