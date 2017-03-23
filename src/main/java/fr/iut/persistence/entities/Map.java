package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Map implements EntityModel<Integer> {

    /**
     * Map's id
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Map's image.
     */
    @Column(nullable = false)
    private Blob image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
