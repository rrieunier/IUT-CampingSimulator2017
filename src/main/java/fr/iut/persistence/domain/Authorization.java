package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "AUTHORIZATION")
public class Authorization {
    @Id
    @Column(name = "label", nullable = false, length = 45)
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
