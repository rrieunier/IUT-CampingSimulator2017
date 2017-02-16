package fr.iut.persistence.entities;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "SUPPLIER")
public class Supplier {

    @Id @GeneratedValue
    @Column
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String website;
}
