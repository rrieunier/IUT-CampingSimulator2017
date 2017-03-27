package fr.iut.view;

import fr.iut.App;
import fr.iut.persistence.entities.Product;
import fr.iut.persistence.entities.Supplier;
import fr.iut.persistence.entities.SupplierProposeProduct;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;

public class ChooseSupplierDialog extends ChoiceDialog<Supplier> {

    public ChooseSupplierDialog(Supplier defaultChoice, ArrayList<Supplier> choices, Product product) {
        super(defaultChoice, choices);

        setTitle("Choix du fournisseur");
        setHeaderText("Choisissez votre fournisseur ci-dessous: ");
        setContentText("Fournisseur: ");

        for (Supplier s : choices) {
            for (SupplierProposeProduct spp : product.getSupplierProposeProducts()) {
                if (spp.getSupplier().equals(s)) {
                    s.setName(s.getName() + " -- " + spp.getSellPrice() + "€/pièce");
                }
            }
        }
    }
}
