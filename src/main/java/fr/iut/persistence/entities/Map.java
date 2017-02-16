package fr.iut.persistence.entities;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "MAP")
public class Map {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private byte[] image;
}
